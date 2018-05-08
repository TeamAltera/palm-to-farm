package com.spring.smart_plant.common.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.spring.smart_plant.user.domain.LoginDTO;

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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "이메일 입력칸이 공백입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "패스워드 입력칸이 공백입니다.");
		
		//추가적인 폼검증
		String email=dto.getEmail();
		String pwd=dto.getPwd();
		//아이디 길이 확인
		if(email.length()>30) {
			errors.rejectValue("email", "lengthsize", "이메일은 30자이하입니다.");
		}
		else{//이메일 형식인지를 확인
			String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";  
			Pattern emailPattern=Pattern.compile(regex);
			Matcher m = emailPattern.matcher(email);
			if(!m.matches()) {
				errors.rejectValue("email", "invalid email", "올바른 이메일 형식이 아닙니다.");
			}
		}
		if(pwd.length()>64) { //SHA256, 패스워드 길이는 64
			errors.rejectValue("pwd", "lengthsize", "올바른 패스워드 형식이 아닙니다.");
		}
	}
}
