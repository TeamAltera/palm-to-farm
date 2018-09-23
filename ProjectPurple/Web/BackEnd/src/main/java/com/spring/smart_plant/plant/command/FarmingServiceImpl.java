package com.spring.smart_plant.plant.command;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.device.command.UrlConnectionServiceImpl;
import com.spring.smart_plant.device.dao.DeviceDAO;
import com.spring.smart_plant.device.domain.CommandDTO;
import com.spring.smart_plant.device.domain.SmartFarmInfoDTO;
import com.spring.smart_plant.log.command.InsertLogServiceImpl;
import com.spring.smart_plant.log.domain.DeviceLogDTO;
import com.spring.smart_plant.plant.dao.PlantDAO;
import com.spring.smart_plant.plant.domain.InfoDTO;
import com.spring.smart_plant.plant.domain.PlantDTO;
import com.spring.smart_plant.sensor.command.DeleteSensorDataServiceImpl;

@Service("farmingService")
public class FarmingServiceImpl implements IPlantService {

	private final String PHP_FORWARD_URL = "/forward.php";

	@Autowired
	private InsertLogServiceImpl insertLogService;

	@Autowired
	private InsertGrowthPlantServiceImpl insertGrowthPlantService;

	@Autowired
	private DeleteGrowthPlantServiceImpl deleteGrowthPlantService;

	@Autowired
	private DeleteSensorDataServiceImpl deleteSensorDataService;
	
	@Autowired
	private SetFarmingInfoServiceImpl setFarmingInfoService;

	@Autowired
	DeviceDAO dao;

	@Autowired
	PlantDAO plantDao;

	@Override
	public ResultDTO execute(Object obj) throws Exception {
		CommandDTO commandSet = (CommandDTO) obj;
		ResultDTO res = ResultDTO.createInstance(false).setMsg("명령 수행에 대한 응답 결과를 받을 수 없습니다.").setData(null);
		try {
			res = setFarming(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			saveLog(commandSet, "err");
			throw e;
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class }) // 이 메소드를 트랜잭션 처리
	private ResultDTO setFarming(Object obj) throws Exception {
		CommandDTO commandSet = (CommandDTO) obj;
		UrlConnectionServiceImpl conn = new UrlConnectionServiceImpl();
		int cmd = commandSet.getCmd();
		int userCode = (int) ConstantJwtService.getJwtService().get("member").get("userCode");
		ResultDTO res = null;
		
		//미들 서버에 명령 전송
		String requestData = "{\"cmd\":\"" + cmd + "\",\"dest\":\"" + commandSet.getDest() + "\",\"apCode\":\""
				+ commandSet.getApCode() + "\"}";

		JSONObject json = conn.request(commandSet.getMiddle(), PHP_FORWARD_URL, "POST", requestData);
		System.out.println((String) json.get("result"));
		String result = (String) json.get("result");
		
		// 수행한 동작에 따라 로그에 저장해야.
		saveLog(commandSet, result);

		if (cmd == 10) {// 수경 재배 시작이라면
			setFarmingInfoService.execute(commandSet.getOptData().getFarm(), commandSet.getApCode(), commandSet.getSfCode());
			dao.updatePort(commandSet.getApCode(), commandSet.getSfCode(), commandSet.getOptData().getSfPort(), 'T');
			dao.updateLED('T', commandSet.getApCode(), commandSet.getSfCode());
			insertGrowthPlantService.execute(
					new InfoDTO(commandSet.getApCode(), commandSet.getSfCode(), commandSet.getOptData().getPlant()));
			res = ResultDTO.createInstance(true)
					.setData(new Object() {
							public PlantDTO plantInfo=plantDao.getPlantInfo(commandSet.getApCode(), commandSet.getSfCode());
							public SmartFarmInfoDTO deviceInfo=(SmartFarmInfoDTO) dao.getSmartPlant(commandSet.getApCode(),commandSet.getSfCode());
						}
					)
					.setMsg("재배를 시작 하였습니다.");
		} else if (cmd == 11) {// 수경 재배 취소라면
			dao.updatePort(commandSet.getApCode(), commandSet.getSfCode(), 0, 'F');
			dao.updateLED('F', commandSet.getApCode(), commandSet.getSfCode());
			plantDao.deletePortInfo(commandSet.getApCode(), commandSet.getSfCode());
			deleteGrowthPlantService.execute(new InfoDTO(commandSet.getApCode(), commandSet.getSfCode(), null));
			
			HashMap<String, Object> map = new HashMap<>();
			map.put("apCode", commandSet.getApCode());
			map.put("sfCode", commandSet.getSfCode());
			deleteSensorDataService.execute(map);
			res = ResultDTO.createInstance(true)
					.setData(new Object() {
						public PlantDTO plantInfo=plantDao.getPlantInfo(commandSet.getApCode(), commandSet.getSfCode());
						public SmartFarmInfoDTO deviceInfo=(SmartFarmInfoDTO) dao.getSmartPlant(commandSet.getApCode(),commandSet.getSfCode());
					}
				)
				.setMsg("재배가 중지 되었습니다.");
		}
		
		return res;
	}

	private void saveLog(CommandDTO commandSet, String result) {
		Timestamp ts = new Timestamp(new Date().getTime());
		int sfCode = commandSet.getSfCode();
		int apCode = commandSet.getApCode();
		String usedIp = commandSet.getUsedIp();
		String actName = getCommandType(commandSet.getCmd());
		char res = getResult(result);
		insertLogService.execute(new DeviceLogDTO(ts, sfCode, apCode, usedIp, actName, res));
	}

	private String getCommandType(int cmd) {
		String output = null;
		switch (cmd) {
		case 10:
			output = "재배시작";
			break;
		case 11:
			output = "재배중지";
			break;
		}
		return output;
	}

	private char getResult(String result) {
		char output = 'T';
		if (result.equals("err"))
			output = 'F';
		return output;
	}
}
