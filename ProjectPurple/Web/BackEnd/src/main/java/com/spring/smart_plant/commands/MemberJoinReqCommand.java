package com.spring.smart_plant.commands;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

import com.spring.smart_plant.DAO.DAO;
import com.spring.smart_plant.DTO.UserInfoDTO;
import com.spring.smart_plant.DTO.Response.MemberJoinResultDTO;

public class MemberJoinReqCommand implements UserRequestCommand{

	@Override
	public Map<String, Object> execute(Model model) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map=new HashMap<>();
		UserInfoDTO dto=(UserInfoDTO)model.asMap().get("userInfo");
		DAO dao=new DAO();
		dao.insertMember(dto);
		map.put("memberJoinResult", new MemberJoinResultDTO(true));
		return map;
	}
}
