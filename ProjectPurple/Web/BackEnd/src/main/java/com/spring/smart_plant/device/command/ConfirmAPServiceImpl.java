package com.spring.smart_plant.device.command;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.device.dao.DeviceDAO;
import com.spring.smart_plant.device.domain.APStatusDTO;

@Service("confirmAPService")
public class ConfirmAPServiceImpl implements IDeviceService {
	private final String PHP_SEARCH_URL = "/search.php";
	private final String SUCCESS_MSG="사용가능한 공유기 입니다.";
	private final String FAIL_MSG="이미 사용중인 공유기 입니다.";
	private final String UNKNOWN_MSG="존재하지 않는 공유기 입니다.";
	private final int SUCCESS_CODE=200;
	private final int FAIL_CODE=201;
	private final int UNKNOWN_CODE=202;
	
	@Autowired
	private DeviceDAO dao;
	
	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		String ip = (String) obj;
		String msg=null;
		APStatusDTO code=new APStatusDTO();
		boolean httpStatus=true;
		if (!isAPExist(ip,dao)){//AP가 존재하지 않는다면
			try {
				JSONObject json=new UrlConnectionCommand().request(ip, PHP_SEARCH_URL, "POST",null);
	            if(json.get("state").equals("FAIL")) {//이미 사용중
	            	code.setCode(FAIL_CODE);
	            	msg=FAIL_MSG;
	            }
	            else { //사용가능한 경우
	            	code.setCode(SUCCESS_CODE);
	            	msg=SUCCESS_MSG;
	            	List<Object> list=((JSONArray)json.get("inner_ip")).toList();//연결된 수경재배기 IP들을 가져옴
	            	code.setInnerIp(list);
	            }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msg=UNKNOWN_MSG;
				code.setCode(UNKNOWN_CODE);
				httpStatus=false;
			}
		}
		else {
			msg=FAIL_MSG;
		}
		return ResultDTO.createInstance(httpStatus).setMsg(msg).setData(code);
	}

	private boolean isAPExist(String ip,DeviceDAO dao) {
		return dao.getAP(ip); 
	}
}
