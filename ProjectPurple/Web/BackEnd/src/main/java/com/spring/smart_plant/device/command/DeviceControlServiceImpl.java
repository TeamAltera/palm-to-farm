package com.spring.smart_plant.device.command;

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
import com.spring.smart_plant.device.dao.DeviceDAO;
import com.spring.smart_plant.device.domain.CommandDTO;
import com.spring.smart_plant.device.domain.SmartFarmInfoDTO;
import com.spring.smart_plant.log.command.InsertLogServiceImpl;
import com.spring.smart_plant.log.domain.DeviceLogDTO;
import com.spring.smart_plant.plant.command.DeleteGrowthPlantServiceImpl;
import com.spring.smart_plant.plant.command.InsertGrowthPlantServiceImpl;
import com.spring.smart_plant.plant.dao.PlantDAO;
import com.spring.smart_plant.plant.domain.InfoDTO;
import com.spring.smart_plant.sensor.command.DeleteSensorDataServiceImpl;

@Service("deviceControlService")
public class DeviceControlServiceImpl implements IDeviceFrontService{
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
	DeviceDAO dao;
	
	@Autowired
	PlantDAO plantDao;
	
	//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class }) // 이 메소드를 트랜잭션 처리
	@Override
	public ResultDTO execute(Object obj) throws Exception {
		// TODO Auto-generated method stub
		CommandDTO commandSet=(CommandDTO)obj;
		UrlConnectionServiceImpl conn=new UrlConnectionServiceImpl();
		int cmd=commandSet.getCmd();
		int userCode=(int)ConstantJwtService.getJwtService().get("member").get("userCode");
		String requestData="{\"cmd\":\"" + cmd 
				+ "\",\"dest\":\""+commandSet.getDest()
				+ "\",\"apCode\":\""+commandSet.getApCode()+ "\"}";
		ResultDTO res=ResultDTO.createInstance(false).setMsg("명령 수행에 대한 응답 결과를 받을 수 없습니다.");
		
		try {
			JSONObject json = conn.request(commandSet.getMiddle(), PHP_FORWARD_URL, "POST", requestData);
			final String result=(String)json.get("result");
			//수행한 동작에 따라 로그에 저장해야.
			saveLog(commandSet, result);
			if(!result.equals("err")) { //명령이 성공했다면
				return ResultDTO.createInstance(true).setMsg("명령이 정상적으로 수행되었습니다.").setData(
						new Object() {
							public String res=result;
							public SmartFarmInfoDTO deviceInfo=(SmartFarmInfoDTO) dao.getSmartPlant(commandSet.getApCode(),commandSet.getSfCode());
						}
				);
			}
			else {//명령이 실패했다면
				return ResultDTO.createInstance(false).setMsg("명령이 실패했습니다.").setData(result);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			saveLog(commandSet, "err");
		}
		return res;
	}
	
	
	private void saveLog(CommandDTO commandSet, String result) {
		Timestamp ts=new Timestamp(new Date().getTime());
		int sfCode=commandSet.getSfCode();
		int apCode=commandSet.getApCode();
		String usedIp=commandSet.getUsedIp();
		String actName=getCommandType(commandSet.getCmd(), sfCode, apCode, result);
		char res=getResult(result);
		insertLogService.execute(new DeviceLogDTO(ts,sfCode,apCode,usedIp,actName,res));
	}
	
	private String getCommandType(int cmd, int sfCode, int apCode, String result) {
		char res = 0;
		String output=null;
		switch (cmd) {
		case 2:
			output="자동모드";
			res='T';
			if(!result.equals("err"))
				dao.updateMode(res,apCode,sfCode);
			break;
		case 3:
			output="수동모드";
			res='F';
			if(!result.equals("err"))
				dao.updateMode(res,apCode,sfCode);
			break;
		case 4:
			output="LED켜기";
			res='T';
			if(!result.equals("err"))
				dao.updateLED(res,apCode,sfCode);
			break;
		case 5:
			output="LED끄기";
			res='F';
			if(!result.equals("err"))
				dao.updateLED(res,apCode,sfCode);
			break;
		case 8:
			output="쿨러켜기";
			res='T';
			if(!result.equals("err"))
				dao.updateCooler(res,apCode,sfCode);
			break;
		case 9:
			output="쿨러끄기";
			res='F';
			if(!result.equals("err"))
				dao.updateCooler(res,apCode,sfCode);
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