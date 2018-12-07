package smart_farm_api.user.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.user.repository.UserMapper;

@Service
@AllArgsConstructor
public class JoinSearchServiceImpl implements IUserService{
	private final static int EMPTY_STRING=2;
	
	private UserMapper userMapper;
	
	@Override
	public ResultDto execute(Object object) {
		String email=object.toString();
		ResultDto result=null;
		
		if(!email.isEmpty()) //공백이 아닌 경우
			result=ResultDto.createInstance(true).setData(userMapper.searchEmail(email));
		else result=ResultDto.createInstance(false).setData(EMPTY_STRING); //공백인 경우
		return result;
	}

}
