package smart_farm_api.device.service;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.common.utils.JwtServiceImpl;
import smart_farm_api.device.domain.CommandDto;
import smart_farm_api.device.domain.SmartFarmInfoDto;
import smart_farm_api.device.repository.DeviceMapper;
import smart_farm_api.log.domain.DeviceLogDto;
import smart_farm_api.log.service.InsertLogServiceImpl;
import smart_farm_api.plant.repository.PlantMapper;
import smart_farm_api.plant.service.DeleteGrowthPlantServiceImpl;
import smart_farm_api.plant.service.InsertGrowthPlantServiceImpl;
import smart_farm_api.sensor.service.DeleteSensorDataServiceImpl;

@Service
@AllArgsConstructor
public class DeviceControlServiceImpl implements IDeviceFrontService {
	private final String PHP_FORWARD_URL = "/forward.php";

	private InsertLogServiceImpl insertLogService;

	private InsertGrowthPlantServiceImpl insertGrowthPlantService;

	private DeleteGrowthPlantServiceImpl deleteGrowthPlantService;

	private DeleteSensorDataServiceImpl deleteSensorDataService;
	
	private UrlConnectionServiceImpl urlConnectionService;

	private DeviceMapper deviceMapper;

	private PlantMapper plantMapper;

	private JwtServiceImpl jwtService;

	// @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
	// Exception.class }) // 이 메소드를 트랜잭션 처리
	@Override
	public ResultDto execute(Object obj) throws Exception {
		// TODO Auto-generated method stub
		CommandDto commandSet = (CommandDto) obj;
		int cmd = commandSet.getCmd();
		int userCode = jwtService.get();
		int apCode = commandSet.getApCode();
		int stamp = commandSet.getStamp();
		
		System.out.println(commandSet.getDest());
		
		String requestData = "{\"cmd\":\"" + cmd + "\",\"dest\":\"" + commandSet.getDest() + "\",\"apCode\":\""
				+ commandSet.getApCode() + "\"}";
		ResultDto res = ResultDto.createInstance(false).setMsg("명령 수행에 대한 응답 결과를 받을 수 없습니다.");

		try {
			
			 JSONObject json = urlConnectionService.request(commandSet.getMiddle(), PHP_FORWARD_URL,
			 "POST", requestData); 
			 final String result=(String)json.get("result");
			 
			//final String result = "OK";

			saveLog(commandSet, result);

			if (!result.equals("err")) { // 명령이 성공했다면
				return ResultDto.createInstance(true).setMsg("명령이 정상적으로 수행되었습니다.").setData(new Object() {
					public String res = result;
					public SmartFarmInfoDto deviceInfo = (SmartFarmInfoDto) deviceMapper
							.getSmartPlant(apCode, stamp);
				});
			} else {// 명령이 실패했다면
				return ResultDto.createInstance(false).setMsg("명령이 실패했습니다.").setData(result);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			saveLog(commandSet, "err");
		}

		return res;
	}

	private void saveLog(CommandDto commandSet, String result) {
		Timestamp ts=new Timestamp(new Date().getTime());
		int stamp=commandSet.getStamp();
		int apCode=commandSet.getApCode();
		String usedIp=commandSet.getUsedIp();
		String actName=getCommandType(commandSet.getCmd(), stamp, apCode, result);
		char res=getResult(result);
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

	private String getCommandType(int cmd, int stamp, int apCode, String result) {
		char res = 0;
		String output = null;
		switch (cmd) {
		case 2:
			output = "자동모드전환";
			res = 'T';
			if (!result.equals("err"))
				deviceMapper.updateMode(res, apCode, stamp);
			break;
		case 3:
			output = "수동모드전환";
			res = 'F';
			if (!result.equals("err"))
				deviceMapper.updateMode(res, apCode, stamp);
			break;
		case 4:
			output = "LED켜기";
			if (!result.equals("err"))
				deviceMapper.updateLED('T','T','T','T', apCode, stamp);
			break;
		case 41:
			output = "2F-A 켜기";
			if (!result.equals("err"))
				deviceMapper.update2floorALED('T', apCode, stamp);
			break;
		case 42:
			output = "2F-B 켜기";
			if (!result.equals("err"))
				deviceMapper.update2floorBLED('T', apCode, stamp);
			break;
		case 43:
			output = "3F-A 켜기";
			if (!result.equals("err"))
				deviceMapper.update3floorALED('T', apCode, stamp);
			break;
		case 44:
			output = "3F-B 켜기";
			if (!result.equals("err"))
				deviceMapper.update3floorBLED('T', apCode, stamp);
			break;
		case 45:
			output = "2F 켜기";
			if (!result.equals("err"))
				deviceMapper.update2floorLED('T','T', apCode, stamp);
			break;
		case 46:
			output = "3F 켜기";
			if (!result.equals("err"))
				deviceMapper.update3floorLED('T','T', apCode, stamp);
			break;
		case 5:
			output = "LED끄기";
			if (!result.equals("err"))
				deviceMapper.updateLED('F','F','F','F', apCode, stamp);
			break;
		case 51:
			output = "2F-A 끄기";
			if (!result.equals("err"))
				deviceMapper.update2floorALED('F',apCode, stamp);
			break;
		case 52:
			output = "2F-B 끄기";
			if (!result.equals("err"))
				deviceMapper.update2floorBLED('F',apCode, stamp);
			break;
		case 53:
			output = "3F-A 끄기";
			if (!result.equals("err"))
				deviceMapper.update3floorALED('F',apCode, stamp);
			break;
		case 54:
			output = "3F-B 끄기";
			if (!result.equals("err"))
				deviceMapper.update3floorBLED('F', apCode, stamp);
			break;
		case 55:
			output = "2F 끄기";
			res = 'T';
			if (!result.equals("err"))
				deviceMapper.update2floorLED('F','F',apCode, stamp);
			break;
		case 56:
			output = "3F 끄기";
			res = 'T';
			if (!result.equals("err"))
				deviceMapper.update3floorLED('F','F',apCode, stamp);
			break;
		case 8:
			output = "쿨러켜기";
			res = 'T';
			if (!result.equals("err"))
				deviceMapper.updateCooler(res,res,res, apCode, stamp);
			break;
		case 81:
			output = "A쿨러켜기";
			res = 'T';
			if (!result.equals("err"))
				deviceMapper.updateCoolerA(res, apCode, stamp);
			break;
		case 82:
			output = "B쿨러켜기";
			res = 'T';
			if (!result.equals("err"))
				deviceMapper.updateCoolerB(res, apCode, stamp);
			break;
		case 83:
			output = "C쿨러켜기";
			res = 'T';
			if (!result.equals("err"))
				deviceMapper.updateCoolerC(res, apCode, stamp);
			break;
		case 9:
			output = "쿨러끄기";
			res = 'F';
			if (!result.equals("err"))
				deviceMapper.updateCooler(res,res,res, apCode, stamp);
			break;
		case 91:
			output = "A쿨러끄기";
			res = 'F';
			if (!result.equals("err"))
				deviceMapper.updateCoolerA(res, apCode, stamp);
			break;
		case 92:
			output = "B쿨러끄기";
			res = 'F';
			if (!result.equals("err"))
				deviceMapper.updateCoolerB(res, apCode, stamp);
			break;
		case 93:
			output = "C쿨러끄기";
			res = 'F';
			if (!result.equals("err"))
				deviceMapper.updateCoolerC(res, apCode, stamp);
			break;
		// case 10:
		// output = "재배시작";
		// break;
		// case 11:
		// output = "재배중지";
		// break;
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