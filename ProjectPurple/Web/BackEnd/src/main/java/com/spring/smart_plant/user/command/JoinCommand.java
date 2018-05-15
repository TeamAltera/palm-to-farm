package com.spring.smart_plant.user.command;

import org.springframework.ui.Model;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.user.dao.UserDAO;
import com.spring.smart_plant.user.domain.UserInfoDTO;

public class JoinCommand implements IUserCommand{

	@Override
	public ResultDTO execute(Model model,UserDAO dao) {
		// TODO Auto-generated method stub
		UserInfoDTO dto=(UserInfoDTO)model.asMap().get("userInfo");
		dao.insertMember(dto);
		return ResultDTO.createInstance(true);
	}
}
