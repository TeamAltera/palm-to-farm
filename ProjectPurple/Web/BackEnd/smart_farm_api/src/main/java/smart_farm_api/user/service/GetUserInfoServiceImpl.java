package smart_farm_api.user.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.common.utils.JwtServiceImpl;
import smart_farm_api.user.domain.UserInfoVo;
import smart_farm_api.user.repository.UserMapper;
import smart_farm_api.user.utils.UserConstants;

@Service
@AllArgsConstructor
public class GetUserInfoServiceImpl implements IUserService{

	private UserMapper userMapper;
	
	private JwtServiceImpl jwtService;
	
	@Override
	public ResultDto execute(Object object) {
		//user의 key값 추출
		int userCode=jwtService.get();
		
		//key값을 통해 사용자 조회
		UserInfoVo userInfo=userMapper.getMember(userCode);
		
		//사용자가 존재한다면
		if(userInfo!=null) {
			return ResultDto.createInstance(true)
					.setMsg(UserConstants.GET_USERINFO_SUCCESS)
					.setData(userInfo);
		}
		//존재하지 않는다면
		else {
			return ResultDto.createInstance(false)
					.setMsg(UserConstants.GET_USERINFO_FAIL);
		}
	}
}
