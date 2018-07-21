package com.spring.smart_plant.mail.command;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.mail.domain.JoinCodeDTO;

@Service("authCodeCheckService")
public class AuthCodeCheckServiceImpl implements IMailService {

	@Override
	public ResultDTO execute(Object obj, HttpSession session) {
		// TODO Auto-generated method stub
		System.out.println("code check");
		String authCode = (String) session.getAttribute("authCode");
		String joinCode = ((JoinCodeDTO) obj).getCode();
		System.out.println(authCode);
		System.out.println(joinCode);
		boolean isEqual = joinCode.equals(authCode);
		if (isEqual)
			session.removeAttribute("authCode");
		return ResultDTO.createInstance(isEqual);
	}
}
