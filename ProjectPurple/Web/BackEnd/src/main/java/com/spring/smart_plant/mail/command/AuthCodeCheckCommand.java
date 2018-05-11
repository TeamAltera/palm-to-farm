package com.spring.smart_plant.mail.command;

import javax.servlet.http.HttpSession;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.mail.domain.JoinCodeDTO;

public class AuthCodeCheckCommand implements IMailCommand{

	@Override
	public ResultDTO execute(Object obj, HttpSession session) {
		// TODO Auto-generated method stub
		System.out.println("code check");
		Object authCode=session.getAttribute("authCode");
		ResultDTO result=null;
		if(authCode!=null) {
			boolean isEqual=authCode.toString().equals((JoinCodeDTO)obj);
			if(isEqual)
				session.removeAttribute("authCode");
			result=ResultDTO.createInstance(isEqual);
		}
		else
			result=ResultDTO.createInstance(false);
		return result;
	}
}
