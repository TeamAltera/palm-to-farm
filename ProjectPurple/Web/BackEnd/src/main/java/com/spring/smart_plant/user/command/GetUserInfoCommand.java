package com.spring.smart_plant.user.command;

import java.util.Map;

import org.springframework.ui.Model;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.user.dao.UserDAO;
import com.spring.smart_plant.user.domain.UserInfoDTO;

public class GetUserInfoCommand implements IUserCommand{

	@Override
	public ResultDTO execute(Model mode, UserDAO dao) {
		// TODO Auto-generated method stub
		Map<String, Object> map=ConstantJwtService.getJwtService().get("member");
		int userCode=(int)map.get("userCode");
		UserInfoDTO userInfo=dao.getMemberInfo(userCode);
		userInfo.setPwd(null);
		return ResultDTO.createInstance(true).setMsg("사용자 정보").setData(userInfo);
	}
	
}
