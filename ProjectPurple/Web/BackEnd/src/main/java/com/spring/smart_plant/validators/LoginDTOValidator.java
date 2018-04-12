package com.spring.smart_plant.validators;

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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberId", "필수입력사항입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "필수입력사항입니다.");
		
		//추가적인 폼검증
		int memberIdLen=dto.getMemberId().length();
		int pwdLen=dto.getPwd().length();
		//아이디 길이 확인
		if(memberIdLen>30) {
			errors.rejectValue("memberId", "lengthsize", "아이디는 30자이하");
		}
		//패스워드 길이 확인
		/*if(pwdLen<8 || pwdLen>15) {
			errors.rejectValue("pwd", "lengthsize", "패스워드는 8~15자리");
		}*/
	}
}
