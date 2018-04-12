package com.spring.smart_plant.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.spring.smart_plant.DTO.JoinEmailDTO;
import com.spring.smart_plant.utills.ConstantMailService;

public class MailSendCommand implements AjaxCommand{

	@Override
	public Map<String, Object> execute(HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session= request.getSession();
		JoinEmailDTO dto=(JoinEmailDTO)request.getAttribute("dto");
		Map<String, Object> map=new HashMap<String, Object>();
		System.out.println(dto.getMail());
		int rand=new Random().nextInt(100000)+10000;
		String subject="수경재배 시스템 인증코드 발급입니다.";
		String content=new StringBuilder().append("귀하의 인증코드: "+String.valueOf(rand)+" 를 주어진 시간내에 입력하세요.").toString();
		map.put("result", ConstantMailService.getMailService().send(subject, content, "sencom1028@gmail.com", dto.getMail(), null));
		session.removeAttribute("code");
		session.setAttribute("code", rand);
		session.setMaxInactiveInterval(3*60);//180초간 세션 유지
		return map;
	}

}
