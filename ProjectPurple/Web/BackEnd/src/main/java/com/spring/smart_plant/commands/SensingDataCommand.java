package com.spring.smart_plant.commands;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public class SensingDataCommand implements UserCommand{
	
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		model.addAttribute("limitTime",((HttpServletRequest)model.asMap().get("request")).getSession(false).getMaxInactiveInterval()*1000);
		model.addAttribute("ver", 2);
	}

}
