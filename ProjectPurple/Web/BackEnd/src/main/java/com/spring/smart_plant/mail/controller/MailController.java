package com.spring.smart_plant.mail.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.mail.command.AuthCodeCheckServiceImpl;
import com.spring.smart_plant.mail.command.MailServiceImpl;
import com.spring.smart_plant.mail.domain.JoinCodeDTO;
import com.spring.smart_plant.mail.domain.JoinEmailDTO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/mail")
public class MailController {
	
	@Autowired
	private MailServiceImpl mailService;
	
	@Autowired
	private AuthCodeCheckServiceImpl authCodeCheckService;

	//메일 발송 시 난수 생성
	@ApiOperation(value = "인증 메일 발송, target:front")
	@PostMapping(value="/send")
	public  ResultDTO sendMail(@RequestBody JoinEmailDTO joinEmailInfo, HttpSession session) {
		return mailService.execute(joinEmailInfo, session);
	}
	
	//세션에 등록해 놓은 체크코드 확인
	@ApiOperation(value = "발송된 인증코드 확인, target:front")
	@PostMapping(value = "/check")
	public ResultDTO joinCodeCheck(@RequestBody JoinCodeDTO joinCodeInfo, HttpSession session){
		return authCodeCheckService.execute(joinCodeInfo,session);
	}
}
