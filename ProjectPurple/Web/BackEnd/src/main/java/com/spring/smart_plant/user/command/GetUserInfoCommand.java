package com.spring.smart_plant.user.command;

import java.util.Map;

import org.springframework.ui.Model;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.user.domain.UserInfoDTO;

public class GetUserInfoCommand implements UserCommand{

	@Override
	public ResultDTO execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map=ConstantJwtService.getJwtService().get("member");
		int userCode=(int)map.get("userCode");
		String email=(String)map.get("email");
		int sfCnt=(int)map.get("sfCnt");
		String firstName=(String)map.get("firstName");
		String secondName=(String)map.get("secondName");
		UserInfoDTO userInfo=new UserInfoDTO(userCode,null,email,sfCnt,firstName,secondName);
		return ResultDTO.createInstance(true).setMsg("사용자 정보").setData(userInfo);
	}
	
}
