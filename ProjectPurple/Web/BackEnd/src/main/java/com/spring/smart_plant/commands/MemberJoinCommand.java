package com.spring.smart_plant.commands;

import org.springframework.ui.Model;

import com.spring.smart_plant.DAO.DAO;
import com.spring.smart_plant.DTO.UserInfoDTO;

public class MemberJoinCommand implements UserCommand{

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		UserInfoDTO dto=(UserInfoDTO)model.asMap().get("userInfo");
		DAO dao=new DAO();
		dao.insertMember(dto);
	}
	
}
