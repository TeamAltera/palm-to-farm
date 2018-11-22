package smart_farm_api.device.service;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.common.service.JwtServiceImpl;
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
public class DeviceControlServiceImpl implements IDeviceFrontService{
	private final String PHP_FORWARD_URL = "/forward.php";	
	
	private InsertLogServiceImpl insertLogService;
	
	private InsertGrowthPlantServiceImpl insertGrowthPlantService;
	
	private DeleteGrowthPlantServiceImpl deleteGrowthPlantService;
	
	private DeleteSensorDataServiceImpl deleteSensorDataService;
	
	private DeviceMapper deviceMapper;
	
	private PlantMapper plantMapper;
	
	private JwtServiceImpl jwtService;
	
	//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class }) // 이 메소드를 트랜잭션 처리
	@Override
	public ResultDto execute(Object obj) throws Exception {
		// TODO Auto-generated method stub
		CommandDto commandSet=(CommandDto)obj;
		UrlConnectionServiceImpl conn=new UrlConnectionServiceImpl();
		int cmd=commandSet.getCmd();
		int userCode=jwtService.get();
		String requestData="{\"cmd\":\"" + cmd 
				+ "\",\"dest\":\""+commandSet.getDest()
				+ "\",\"apCode\":\""+commandSet.getApCode()+ "\"}";
		ResultDto res=ResultDto.createInstance(false).setMsg("명령 수행에 대한 응답 결과를 받을 수 없습니다.");
		
		try {
			JSONObject json = conn.request(commandSet.getMiddle(), PHP_FORWARD_URL, "POST", requestData);
			final String result=(String)json.get("result");
			//수행한 동작에 따라 로그에 저장해야.
			saveLog(commandSet, result);
			if(!result.equals("err")) { //명령이 성공했다면
				return ResultDto.createInstance(true).setMsg("명령이 정상적으로 수행되었습니다.").setData(
						new Object() {
							public String res=result;
							public SmartFarmInfoDto deviceInfo=(SmartFarmInfoDto) deviceMapper.getSmartPlant(commandSet.getApCode(),commandSet.getSfCode());
						}
				);
			}
			else {//명령이 실패했다면
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
		int sfCode=commandSet.getSfCode();
		int apCode=commandSet.getApCode();
		String usedIp=commandSet.getUsedIp();
		String actName=getCommandType(commandSet.getCmd(), sfCode, apCode, result);
		char res=getResult(result);
		insertLogService.execute(new DeviceLogDto(ts,sfCode,apCode,usedIp,actName,res));
	}
	
	private String getCommandType(int cmd, int sfCode, int apCode, String result) {
		char res = 0;
		String output=null;
		switch (cmd) {
		case 2:
			output="자동모드";
			res='T';
			if(!result.equals("err"))
				deviceMapper.updateMode(res,apCode,sfCode);
			break;
		case 3:
			output="수동모드";
			res='F';
			if(!result.equals("err"))
				deviceMapper.updateMode(res,apCode,sfCode);
			break;
		case 4:
			output="LED켜기";
			res='T';
			if(!result.equals("err"))
				deviceMapper.updateLED(res,apCode,sfCode);
			break;
		case 5:
			output="LED끄기";
			res='F';
			if(!result.equals("err"))
				deviceMapper.updateLED(res,apCode,sfCode);
			break;
		case 8:
			output="쿨러켜기";
			res='T';
			if(!result.equals("err"))
				deviceMapper.updateCooler(res,apCode,sfCode);
			break;
		case 9:
			output="쿨러끄기";
			res='F';
			if(!result.equals("err"))
				deviceMapper.updateCooler(res,apCode,sfCode);
			break;
//		case 10:
//			output = "재배시작";
//			break;
//		case 11:
//			output = "재배중지";
//			break;
		}
		return output;
	}
	
	private char getResult(String result) {
		char output='T';
		if(result.equals("err")) 
			output='F';
		return output;
	}
}