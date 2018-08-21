package com.spring.smart_plant.user.command;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.user.dao.UserDAO;
import com.spring.smart_plant.user.domain.UserInfoDTO;

@Service("getUserInfoService")
public class GetUserInfoServiceImpl implements IUserService{

	@Autowired
	private UserDAO dao;
	
	@Override
	public ResultDTO execute(Model mode) {
		// TODO Auto-generated method stub
		Map<String, Object> map=ConstantJwtService.getJwtService().get("member");
		int userCode=(int)map.get("userCode");
		UserInfoDTO userInfo=dao.getMemberInfo(userCode);
		if(userInfo!=null) {
			userInfo.setPwd(null);
			return ResultDTO.createInstance(true).setMsg("사용자 정보").setData(userInfo);
		}
		else {
			return ResultDTO.createInstance(false).setMsg("정보를 받아오는데 실패하였습니다.");
		}
	}
	
}
