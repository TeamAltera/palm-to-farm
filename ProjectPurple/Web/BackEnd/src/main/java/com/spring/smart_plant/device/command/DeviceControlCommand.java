package com.spring.smart_plant.device.command;

import java.io.IOException;

import org.json.JSONObject;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.device.domain.CommandDTO;

public class DeviceControlCommand implements DeviceCommand{
	private final String PHP_FORWARD_URL = "/forward.php";
	
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
		System.out.println(requestData);
		try {
			JSONObject json = conn.request(commandSet.getMiddle(), PHP_FORWARD_URL, "POST", requestData);
			/*System.out.println(json.get("cmd"));
			System.out.println(json.get("dest"));*/
			return ResultDTO.createInstance(true).setMsg("명령이 정상적으로 수행되었습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultDTO.createInstance(false).setMsg("명령 수행에 대한 응답 결과를 받을 수 없습니다.");
	}
	
}