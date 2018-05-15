package com.spring.smart_plant.user.command;

import org.springframework.ui.Model;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.user.dao.UserDAO;
import com.spring.smart_plant.user.domain.EmailDTO;

public class JoinSearchCommand implements IUserCommand{
	private final static int EMPTY_STRING=2;
	
	@Override
	public ResultDTO execute(Model model,UserDAO dao) {
		// TODO Auto-generated method stub
		String email=((EmailDTO)model.asMap().get("emailInfo")).getEmail();
		ResultDTO result=null;
		if(!email.isEmpty()) //공백이 아닌 경우
			result=ResultDTO.createInstance(true).setData(dao.searchEmail(email));
		else result=ResultDTO.createInstance(false).setData(EMPTY_STRING); //공백인 경우
		return result;
	}

}
