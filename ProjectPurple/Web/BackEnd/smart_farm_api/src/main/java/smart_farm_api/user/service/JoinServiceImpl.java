package smart_farm_api.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.user.domain.UserInfoDto;
import smart_farm_api.user.repository.UserMapper;
import smart_farm_api.user.utils.UserConstants;

@Service
@AllArgsConstructor
public class JoinServiceImpl implements IUserService{

	private UserMapper userMapper;
	
	private BCryptPasswordEncoder encoder;
	
	@Override
	public ResultDto execute(Object object) {
		// TODO Auto-generated method stub
		UserInfoDto userInfoDto=(UserInfoDto)object;
		
		//패스워드 인코딩
		String encPwd=encoder.encode(userInfoDto.getPwd());
		userInfoDto.setPwd(encPwd);
		userMapper.insertMember(userInfoDto);
		return ResultDto.createInstance(true).setMsg(UserConstants.CREATE_USERINFO_SUCCESS);
	}
}
