package com.spring.smart_plant.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.spring.smart_plant.DAO.DAO;
import com.spring.smart_plant.DTO.UserInfoDTO;

public class MainCommand implements UserCommand{

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		HttpSession session=((HttpServletRequest)model.asMap().get("request")).getSession(false);
		UserInfoDTO dto=(UserInfoDTO)session.getAttribute("userInfo");
		DAO dao=new DAO();
		model.addAttribute("SF", dao.getAllSmartPlant(dto.getUserCode()));
		model.addAttribute("limitTime",session.getMaxInactiveInterval()*1000);
	}
}
