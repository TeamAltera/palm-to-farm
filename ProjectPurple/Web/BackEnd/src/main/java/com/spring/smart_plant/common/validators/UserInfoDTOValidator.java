package com.spring.smart_plant.common.validators;

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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email is blank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "password is blank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "email is blank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "secondName", "password is blank");
		
		UserInfoDTO dto=(UserInfoDTO)obj;
		String email=dto.getEmail();
		String pwd=dto.getPwd();
		String firstName=dto.getFirstName();
		String secondName=dto.getSecondName();
		
	}
}
