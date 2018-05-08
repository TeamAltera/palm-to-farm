package com.spring.smart_plant.user.command;

import org.springframework.ui.Model;

import com.spring.smart_plant.common.dao.DAO;
import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.user.domain.UserInfoDTO;

public class JoinCommand implements UserCommand{

	@Override
	public ResultDTO execute(Model model) {
		// TODO Auto-generated method stub
		UserInfoDTO dto=(UserInfoDTO)model.asMap().get("userInfo");
		DAO dao=new DAO();
		dao.insertMember(dto);
		return ResultDTO.createInstance(true);
	}
}
