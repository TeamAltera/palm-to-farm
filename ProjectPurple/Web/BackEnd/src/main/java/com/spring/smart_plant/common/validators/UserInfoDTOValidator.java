package com.spring.smart_plant.common.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.spring.smart_plant.user.domain.UserInfoDTO;

public class UserInfoDTOValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.toString());
		return UserInfoDTO.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "이메일 입력칸이 공백입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "패스워드 입력칸이 공백입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "성 입력칸이 공백입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "secondName", "이름 입력칸이 공백입니다.");
		
		UserInfoDTO dto=(UserInfoDTO)obj;
		String email=dto.getEmail();
		String pwd=dto.getPwd();
		String firstName=dto.getFirstName();
		String secondName=dto.getSecondName();
		
		emailValidation(email, errors);
		pwdValidation(pwd, errors);
		firstNameValidation(firstName, errors);
		secondNameValidation(secondName, errors);
	}
	
	private void emailValidation(String email, Errors errors) {
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
	}
	
	private void pwdValidation(String pwd, Errors errors) {
		if(pwd.length()>64) { //SHA256, 패스워드 길이는 64
			errors.rejectValue("pwd", "lengthsize", "올바른 패스워드 형식이 아닙니다.");
		}
	}
	
	private void firstNameValidation(String firstName, Errors errors) {
		if(firstName.length()>16) { //SHA256, 패스워드 길이는 64
			errors.rejectValue("firstName", "lengthsize", "올바른 성 형식이 아닙니다.");
		}
	}
	
	private void secondNameValidation(String secondName, Errors errors) {
		if(secondName.length()>30) { //SHA256, 패스워드 길이는 64
			errors.rejectValue("secondName", "lengthsize", "올바른 이름 형식이 아닙니다.");
		}
	}
}
