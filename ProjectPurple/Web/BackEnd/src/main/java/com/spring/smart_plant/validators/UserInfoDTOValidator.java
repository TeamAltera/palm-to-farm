package com.spring.smart_plant.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.spring.smart_plant.DTO.UserInfoDTO;

public class UserInfoDTOValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.toString());
		return UserInfoDTO.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}

}
