package com.spring.smart_plant.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

public class LogoutCommand implements UserCommand{

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		HttpSession session=((HttpServletRequest)model.asMap().get("request")).getSession(false);
		if(session!=null && session.getAttribute("userInfo")!=null) {
			System.out.println("session is invalidate");
			session.invalidate();
		}
	}

}
