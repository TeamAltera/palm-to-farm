package com.spring.smart_plant.common.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.spring.smart_plant.device.domain.CommandDTO;

public class CommandDTOValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0);
		return CommandDTO.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		// TODO Auto-generated method stub
		CommandDTO commandSet=(CommandDTO)arg0;
		String middleIp=commandSet.getMiddle();
		String destIp=commandSet.getDest();
		int cmd=commandSet.getCmd();
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "middle", "알맞지 않은 공유기 주소 형식 입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dest", "알맞지 않은 장치 주소 형식 입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cmd", "알맞지 않은 명령어 형식 입니다.");
		if(!ipValidation(middleIp))
			errors.rejectValue("middle","middle", "알맞지 않은 공유기 주소 형식 입니다.");
		if(!ipValidation(destIp))
			errors.rejectValue("dest","dest", "알맞지 않은 장치 주소 형식 입니다.");
	}
	
	private boolean ipValidation(String ip) {
		//IPv4 정규표현식
		String regex="^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
		Pattern ipPattern=Pattern.compile(regex);
		Matcher m = ipPattern.matcher(ip);
		return m.matches();
	}
}
