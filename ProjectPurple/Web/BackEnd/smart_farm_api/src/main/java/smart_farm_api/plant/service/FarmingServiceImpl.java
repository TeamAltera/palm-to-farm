package smart_farm_api.plant.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import smart_farm_api.common.ResultDto;
import smart_farm_api.common.service.JwtServiceImpl;
import smart_farm_api.device.domain.CommandDto;
import smart_farm_api.device.domain.SmartFarmInfoDto;
import smart_farm_api.device.repository.DeviceMapper;
import smart_farm_api.device.service.UrlConnectionServiceImpl;
import smart_farm_api.log.domain.DeviceLogDto;
import smart_farm_api.log.service.InsertLogServiceImpl;
import smart_farm_api.plant.domain.InfoDto;
import smart_farm_api.plant.domain.PlantDto;
import smart_farm_api.plant.repository.PlantMapper;
import smart_farm_api.sensor.service.DeleteSensorDataServiceImpl;

@Service("farmingService")
public class FarmingServiceImpl implements IPlantService {

	private final String PHP_FORWARD_URL = "/forward.php";

	private InsertLogServiceImpl insertLogService;

	private InsertGrowthPlantServiceImpl insertGrowthPlantService;

	private DeleteGrowthPlantServiceImpl deleteGrowthPlantService;

	private DeleteSensorDataServiceImpl deleteSensorDataService;
	
	private SetFarmingInfoServiceImpl setFarmingInfoService;

	private DeviceMapper deviceMapper;

	private PlantMapper plantMapper;
	
	private JwtServiceImpl jwtService;

	@Override
	public ResultDto execute(Object obj) throws Exception {
		CommandDto commandSet = (CommandDto) obj;
		ResultDto res = ResultDto.createInstance(false).setMsg("명령 수행에 대한 응답 결과를 받을 수 없습니다.").setData(null);
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
	private ResultDto setFarming(Object obj) throws Exception {
		CommandDto commandSet = (CommandDto) obj;
		UrlConnectionServiceImpl conn = new UrlConnectionServiceImpl();
		int cmd = commandSet.getCmd();
		int userCode = jwtService.get();
		ResultDto res = null;
		
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
			deviceMapper.updatePort(commandSet.getApCode(), commandSet.getSfCode(), commandSet.getOptData().getSfPort(), 'T');
			deviceMapper.updateLED('T', commandSet.getApCode(), commandSet.getSfCode());
			insertGrowthPlantService.execute(
					new InfoDto(commandSet.getApCode(), commandSet.getSfCode(), commandSet.getOptData().getPlant()));
			res = ResultDto.createInstance(true)
					.setData(new Object() {
							public PlantDto plantInfo=plantMapper.getPlantInfo(commandSet.getApCode(), commandSet.getSfCode());
							public SmartFarmInfoDto deviceInfo=(SmartFarmInfoDto) deviceMapper.getSmartPlant(commandSet.getApCode(),commandSet.getSfCode());
						}
					)
					.setMsg("재배를 시작 하였습니다.");
		} else if (cmd == 11) {// 수경 재배 취소라면
			deviceMapper.updatePort(commandSet.getApCode(), commandSet.getSfCode(), 0, 'F');
			deviceMapper.updateLED('F', commandSet.getApCode(), commandSet.getSfCode());
			plantMapper.deletePortInfo(commandSet.getApCode(), commandSet.getSfCode());
			deleteGrowthPlantService.execute(new InfoDto(commandSet.getApCode(), commandSet.getSfCode(), null));
			
			HashMap<String, Object> map = new HashMap<>();
			map.put("apCode", commandSet.getApCode());
			map.put("sfCode", commandSet.getSfCode());
			deleteSensorDataService.execute(map);
			res = ResultDto.createInstance(true)
					.setData(new Object() {
						public PlantDto plantInfo=plantMapper.getPlantInfo(commandSet.getApCode(), commandSet.getSfCode());
						public SmartFarmInfoDto deviceInfo=(SmartFarmInfoDto) deviceMapper.getSmartPlant(commandSet.getApCode(),commandSet.getSfCode());
					}
				)
				.setMsg("재배가 중지 되었습니다.");
		}
		
		return res;
	}

	private void saveLog(CommandDto commandSet, String result) {
		Timestamp ts = new Timestamp(new Date().getTime());
		int sfCode = commandSet.getSfCode();
		int apCode = commandSet.getApCode();
		String usedIp = commandSet.getUsedIp();
		String actName = getCommandType(commandSet.getCmd());
		char res = getResult(result);
		insertLogService.execute(new DeviceLogDto(ts, sfCode, apCode, usedIp, actName, res));
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
