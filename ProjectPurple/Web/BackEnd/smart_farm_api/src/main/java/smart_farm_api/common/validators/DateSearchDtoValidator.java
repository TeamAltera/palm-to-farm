package smart_farm_api.common.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import smart_farm_api.common.DateSearchDto;

public class DateSearchDtoValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0);
		return DateSearchDto.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		// TODO Auto-generated method stub
		String date=((DateSearchDto)arg0).getDate();
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "date", "invalid date format");
		//IPv4 정규표현식
		String regex="[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]";
		Pattern ipPattern=Pattern.compile(regex);
		Matcher m = ipPattern.matcher(date);
		if(!m.matches()) { //ip형식이 아니라면 에러 발생
			errors.rejectValue("date","invalid date", "invalid date format");
		}
	}

}
