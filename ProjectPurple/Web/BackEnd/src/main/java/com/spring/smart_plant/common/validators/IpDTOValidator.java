package com.spring.smart_plant.common.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.spring.smart_plant.device.domain.IpDTO;

public class IpDTOValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0);
		return IpDTO.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		// TODO Auto-generated method stub
		String ip=((IpDTO)arg0).getIp();
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ip", "invalid ip form");
		//IPv4 정규표현식
		String regex="^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
		Pattern ipPattern=Pattern.compile(regex);
		Matcher m = ipPattern.matcher(ip);
		if(!m.matches()) { //ip형식이 아니라면 에러 발생
			errors.rejectValue("ip","invalid ip", "invalid ip form");
		}
	}

}
