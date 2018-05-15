package com.spring.smart_plant.mail.command;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantMailService;
import com.spring.smart_plant.mail.domain.JoinEmailDTO;

public class MailSendCommand implements IMailCommand{
	private static final String SUBJECT="수경재배 시스템 인증코드 발급입니다.";
	private static final String PUBLISHER="sencom1028@gmail.com"; //추후 아마존 SES서비스 적용해야
	
	@Override
	public ResultDTO execute(Object obj, HttpSession session) {
		// TODO Auto-generated method stub
		String subscriber=((JoinEmailDTO)obj).getMail();
		System.out.println(subscriber);
		
		String authCode=createRandString();
		String content=new StringBuilder().append("귀하의 인증코드: "+authCode+" 를 주어진 시간내에 입력하세요.").toString();
		session.removeAttribute("authCode"); //기존에 세션에 등록된 코드 삭제
		session.setAttribute("authCode", authCode); //새로 등록
		session.setMaxInactiveInterval(3*60);//180초간 세션 유지
		return ResultDTO.createInstance(ConstantMailService.getMailService().send(SUBJECT, content, PUBLISHER, subscriber, null));
	}

	private String createRandString() {
		return RandomStringUtils.randomAlphanumeric(10);
	}
}
