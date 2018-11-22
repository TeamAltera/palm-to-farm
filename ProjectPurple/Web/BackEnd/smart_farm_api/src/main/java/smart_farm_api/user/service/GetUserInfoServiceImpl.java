package smart_farm_api.user.service;



import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.common.service.JwtServiceImpl;
import smart_farm_api.user.domain.UserInfoDto;
import smart_farm_api.user.repository.UserMapper;

@Service
@AllArgsConstructor
public class GetUserInfoServiceImpl implements IUserService{

	private UserMapper userMapper;
	
	private JwtServiceImpl jwtService;
	
	@Override
	public ResultDto execute(Model mode) {
		// TODO Auto-generated method stub
		int userCode=jwtService.get();
		UserInfoDto userInfo=userMapper.getMemberInfo(userCode);
		if(userInfo!=null) {
			userInfo.setPwd(null);
			return ResultDto.createInstance(true).setMsg("사용자 정보").setData(userInfo);
		}
		else {
			return ResultDto.createInstance(false).setMsg("정보를 받아오는데 실패하였습니다.");
		}
	}
	
}
