package com.spring.smart_plant.commands;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.ui.Model;

import com.spring.smart_plant.DAO.DAO;
import com.spring.smart_plant.DTO.LoginDTO;
import com.spring.smart_plant.DTO.UserInfoDTO;

public class LoginCommand implements UserCommand{
	
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map=model.asMap();
		LoginDTO loginInfo=(LoginDTO)map.get("loginInfo");
		HttpSession session=(HttpSession)map.get("session");
		DAO dao=new DAO();
		UserInfoDTO dto=dao.searchMember(loginInfo);
		if(dto!=null) {
			System.out.println("correct id/pwd");
			if(session.getAttribute("userInfo")==null) {
				System.out.println("session is register");
				session.setAttribute("userInfo", dto);
			}
			model.addAttribute("forwardPage","redirect:main");
			return;
		}
		model.addAttribute("forwardPage","redirect:login");
	}

}
