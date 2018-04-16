package com.spring.smart_plant.mail.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.service.mail.MailService;
import com.spring.smart_plant.common.utills.ConstantMailService;
import com.spring.smart_plant.mail.command.AuthCodeCheckCommand;
import com.spring.smart_plant.mail.command.MailSendCommand;
import com.spring.smart_plant.mail.domain.JoinCodeDTO;
import com.spring.smart_plant.mail.domain.JoinEmailDTO;

@RestController
@RequestMapping("/mail")
public class MailController {
	private MailService mailService;

	@Autowired
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
		ConstantMailService.setMailService(mailService);
	}

	//메일 발송 시 난수 생성
	@PostMapping(value="/send")
	public  ResultDTO sendMail(JoinEmailDTO joinEmailInfo, HttpSession session) {
		return new MailSendCommand().execute(joinEmailInfo, session);
	}
	
	//세션에 등록해 놓은 체크코드 확인
	@PostMapping(value = "/check")
	public ResultDTO joinCodeCheck(JoinCodeDTO joinCodeInfo, HttpSession session){
		return new AuthCodeCheckCommand().execute(joinCodeInfo,session);
	}
}
