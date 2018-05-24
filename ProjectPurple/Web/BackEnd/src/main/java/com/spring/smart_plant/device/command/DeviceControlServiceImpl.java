package com.spring.smart_plant.device.command;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.device.dao.DeviceDAO;
import com.spring.smart_plant.device.domain.CommandDTO;
import com.spring.smart_plant.log.command.InsertLogServiceImpl;
import com.spring.smart_plant.log.domain.DeviceLogDTO;

@Service("deviceControlService")
public class DeviceControlServiceImpl implements IDeviceService{
	private final String PHP_FORWARD_URL = "/forward.php";	
	
	@Autowired
	private InsertLogServiceImpl insertLogService;
	
	@Autowired
	DeviceDAO dao;
	
	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		CommandDTO commandSet=(CommandDTO)obj;
		UrlConnectionCommand conn=new UrlConnectionCommand();
		int cmd=commandSet.getCmd();
		int userCode=(int)ConstantJwtService.getJwtService().get("member").get("userCode");
		String requestData="{\"cmd\":\"" + cmd 
				+ "\",\"dest\":\""+commandSet.getDest()
				+ "\",\"userCode\":\""+userCode+ "\"}";
		try {
			JSONObject json = conn.request(commandSet.getMiddle(), PHP_FORWARD_URL, "POST", requestData);
			String result=(String)json.get("result");
			//수행한 동작에 따라 로그에 저장해야.
			saveLog(commandSet, result);
			if(!result.equals("err")) {//명령이 성공했다면
				return ResultDTO.createInstance(true).setMsg("명령이 정상적으로 수행되었습니다.").setData(result);
			}
			else {//명령이 실패했다면
				return ResultDTO.createInstance(false).setMsg("명령이 실패했습니다.").setData(result);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		saveLog(commandSet, "err");
		return ResultDTO.createInstance(false).setMsg("명령 수행에 대한 응답 결과를 받을 수 없습니다.");
	}
	
	
	private void saveLog(CommandDTO commandSet, String result) {
		Timestamp ts=new Timestamp(new Date().getTime());
		int sfCode=commandSet.getSfCode();
		String usedIp=commandSet.getUsedIp();
		String actName=getCommandType(commandSet.getCmd());
		char res=getResult(result);
		insertLogService.execute(new DeviceLogDTO(ts,sfCode,usedIp,actName,res));
	}
	
	private String getCommandType(int cmd) {
		String output=null;
		switch (cmd) {
		case 2:
			output="자동모드";
			break;
		case 3:
			output="수동모드";
			break;
		case 4:
			output="LED켜기";
			break;
		case 5:
			output="LED끄기";
			break;
		}
		return output;
	}
	
	private char getResult(String result) {
		char output='Y';
		if(result.equals("err")) 
			output='N';
		return output;
	}
}