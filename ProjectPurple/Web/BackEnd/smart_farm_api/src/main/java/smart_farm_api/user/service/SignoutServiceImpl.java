package smart_farm_api.user.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.user.utils.UserConstants;

@Service
@AllArgsConstructor
public class SignoutServiceImpl implements IUserService{
	
	@Override
	public ResultDto execute(Object object) {

		return ResultDto.createInstance(true).setMsg(UserConstants.SIGNOUT_SUCCESS);
	}

}
