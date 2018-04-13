package com.spring.smart_plant.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.smart_plant.DTO.JoinCodeDTO;
import com.spring.smart_plant.DTO.JoinEmailDTO;
import com.spring.smart_plant.commands.AjaxCommand;
import com.spring.smart_plant.commands.JoinCodeCheckCommand;
import com.spring.smart_plant.commands.MailSendCommand;
import com.spring.smart_plant.services.mail.MailService;
import com.spring.smart_plant.utills.ConstantMailService;

@Controller
public class MailController {
	private MailService mailService;

	@Autowired
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
		ConstantMailService.setMailService(mailService);
	}

	//메일 발송 시 난수 생성
	@RequestMapping(value="/send_email", produces="application/json", method=RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> sendMail(@RequestBody JoinEmailDTO dto, HttpServletRequest request) {
		request.setAttribute("dto", dto);
		AjaxCommand command=new MailSendCommand();
		return command.execute(request);
	}
	
	//세션에 등록해 놓은 체크코드 확인
	@RequestMapping(value = "/joinCodeCheck",produces="application/json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> joinCodeCheck(@RequestBody JoinCodeDTO dto, HttpServletRequest request){
		request.setAttribute("code", dto.getCode());
		AjaxCommand command=new JoinCodeCheckCommand();
		return command.execute(request);
	}
}
