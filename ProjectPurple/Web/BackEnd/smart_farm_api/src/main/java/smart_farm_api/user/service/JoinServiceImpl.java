package smart_farm_api.user.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.user.domain.UserInfoDto;
import smart_farm_api.user.repository.UserMapper;

@Service
@AllArgsConstructor
public class JoinServiceImpl implements IUserService{

	private UserMapper userMapper;
	
	@Override
	public ResultDto execute(Model model) {
		// TODO Auto-generated method stub
		UserInfoDto dto=(UserInfoDto)model.asMap().get("userInfo");
		userMapper.insertMember(dto);
		return ResultDto.createInstance(true);
	}
}
