package com.spring.smart_plant.commands;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.spring.smart_plant.DTO.Response.LogoutResultDTO;

public class LogoutReqCommand implements UserRequestCommand{

	@Override
	public Map<String, Object> execute(Model model) {
		// TODO Auto-generated method stub
		HashMap<String, Object> hashMap=new HashMap<>();
		HttpSession session=((HttpServletRequest)model.asMap().get("request")).getSession(false);
		if(session!=null && session.getAttribute("userInfo")!=null) {
			System.out.println("session is invalidate");
			session.invalidate();
		}
		hashMap.put("logoutResult", new LogoutResultDTO(true));
		return hashMap;
	}

}
