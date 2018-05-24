package com.spring.smart_plant.user.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.user.dao.UserDAO;
import com.spring.smart_plant.user.domain.UserInfoDTO;

@Service("joinService")
public class JoinServiceImpl implements IUserService{

	@Autowired
	private UserDAO dao;
	
	@Override
	public ResultDTO execute(Model model) {
		// TODO Auto-generated method stub
		UserInfoDTO dto=(UserInfoDTO)model.asMap().get("userInfo");
		dao.insertMember(dto);
		return ResultDTO.createInstance(true);
	}
}
