package smart_farm_api.user.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.user.repository.UserMapper;

@Service
@AllArgsConstructor
public class JoinSearchServiceImpl implements IUserService{
	private final static int EMPTY_STRING=2;
	
	private UserMapper userMapper;
	
	@Override
	public ResultDto execute(Model model) {
		// TODO Auto-generated method stub
		String email=model.asMap().get("emailInfo").toString();
		System.out.println(email);
		ResultDto result=null;
		if(!email.isEmpty()) //공백이 아닌 경우
			result=ResultDto.createInstance(true).setData(userMapper.searchEmail(email));
		else result=ResultDto.createInstance(false).setData(EMPTY_STRING); //공백인 경우
		return result;
	}

}
