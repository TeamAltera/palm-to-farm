package smart_farm_api.plant.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.common.utils.JwtServiceImpl;
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

@Service
@AllArgsConstructor
public class FarmingServiceImpl implements IPlantService {

	private final String PHP_FORWARD_URL = "/forward.php";

	private InsertLogServiceImpl insertLogService;

	private InsertGrowthPlantServiceImpl insertGrowthPlantService;

	private DeleteGrowthPlantServiceImpl deleteGrowthPlantService;

	private DeleteSensorDataServiceImpl deleteSensorDataService;

	private SetFarmingInfoServiceImpl setFarmingInfoService;
	
	private UrlConnectionServiceImpl urlConnectionService;

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
		int cmd = commandSet.getCmd();
		int apCode = commandSet.getApCode();
		int stamp = commandSet.getStamp();
		ResultDto res = null;

		// 미들 서버에 명령 전송
		String requestData = "{\"cmd\":\"" + cmd + "\",\"dest\":\"" + commandSet.getDest() + "\",\"apCode\":\""
				+ commandSet.getApCode() + "\"}";

		 JSONObject json = urlConnectionService.request(commandSet.getMiddle(), PHP_FORWARD_URL,"POST", requestData); 
		 System.out.println((String) json.get("result")); 
		 String result = (String) json.get("result");
		 
		//String result = "OK";

		if (cmd == 10) {// 수경 재배 시작이라면
			System.out.println("farm start");
			setFarmingInfoService.execute(commandSet.getOptData().getFarm(), apCode, stamp);
			deviceMapper.updatePort(apCode, stamp, commandSet.getOptData().getSfPort(), 'T');
			deviceMapper.updateLED('T','T','T','T', apCode, stamp);
			insertGrowthPlantService.execute(new InfoDto(apCode, stamp, commandSet.getOptData().getPlant()));

			res = ResultDto.createInstance(true).setData(new Object() {
				public PlantDto plantInfo = plantMapper.getPlantInfo(apCode, stamp);
				public SmartFarmInfoDto deviceInfo = (SmartFarmInfoDto) deviceMapper.getSmartPlant(apCode, stamp);
			}).setMsg("재배를 시작 하였습니다.");
		} else if (cmd == 11) {// 수경 재배 취소라면
			System.out.println("farm stop");
			deviceMapper.updatePort(apCode, stamp, 0, 'F');
			deviceMapper.updateLED('F','F','F','F', apCode, stamp);
			plantMapper.deletePortInfo(apCode, stamp);
			deleteGrowthPlantService.execute(InfoDto.builder().apCode(apCode).stamp(stamp).build());

			HashMap<String, Object> map = new HashMap<>();
			map.put("apCode", apCode);
			map.put("stamp", stamp);
			//deleteSensorDataService.execute(map);
			res = ResultDto.createInstance(true).setData(new Object() {
				public PlantDto plantInfo = plantMapper.getPlantInfo(apCode, stamp);
				public SmartFarmInfoDto deviceInfo = (SmartFarmInfoDto) deviceMapper.getSmartPlant(apCode, stamp);
			}).setMsg("재배가 중지 되었습니다.");
		}
		
		// 수행한 동작에 따라 로그에 저장해야.
		saveLog(commandSet, result);

		return res;
	}

	private void saveLog(CommandDto commandSet, String result) {
		Timestamp ts = new Timestamp(new Date().getTime());
		int stamp = commandSet.getStamp();
		int apCode = commandSet.getApCode();
		String usedIp = commandSet.getUsedIp();
		String actName = getCommandType(commandSet.getCmd());
		char res = getResult(result);
		insertLogService.execute(
				DeviceLogDto.builder()
					.stamp(stamp)
					.apCode(apCode)
					.usedIp(usedIp)
					.actName(actName)
					.result(res)
					.usedDate(ts)
					.build()
				);
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
