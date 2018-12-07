package smart_farm_api.user.utils;

import org.springframework.validation.BindingResult;

import io.swagger.annotations.ApiOperation;
import smart_farm_api.common.ResultDto;
import smart_farm_api.user.domain.UserInfoDto;

public interface UserDocs {
	
	@ApiOperation(value = "회원 가입, target:front")
	ResultDto create(UserInfoDto userInfo, BindingResult result);
	
	@ApiOperation(value = "사용자 정보 조회, target:front")
	ResultDto getUserInfo();
	
	@ApiOperation(value = "등록된 이메일 인지 조회, target:front")
	ResultDto isExist(String email);
	
	@ApiOperation(value = "로그아웃, target:front")
	ResultDto signout();
}
