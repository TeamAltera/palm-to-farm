package com.spring.smart_plant.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.spring.smart_plant.DTO.LoginDTO;

public class LoginDTOValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.toString());
		return LoginDTO.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		LoginDTO dto=(LoginDTO)obj;
		//해당필드값이 null인지만 체크
		//default msg미지정시 exception
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email is blank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "password is blank");
		
		//추가적인 폼검증
		int memberIdLen=dto.getEmail().length();
		int pwdLen=dto.getPwd().length();
		//아이디 길이 확인
		if(memberIdLen>30) {
			errors.rejectValue("email", "lengthsize", "이메일은 30자이하");
		}
		else{
			String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";  
			Pattern emailPattern=Pattern.compile(regex);
			Matcher m = emailPattern.matcher(dto.getEmail());
			if(!m.matches()) {
				errors.rejectValue("email", "invalid email", "invalid email form");
			}
		}
		if(pwdLen>64) {
			errors.rejectValue("pwd", "lengthsize", "password length is not valid");
		}
	}
}
