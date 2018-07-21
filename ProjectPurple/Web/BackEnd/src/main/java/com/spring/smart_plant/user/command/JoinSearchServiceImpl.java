package com.spring.smart_plant.user.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.user.dao.UserDAO;
import com.spring.smart_plant.user.domain.EmailDTO;

@Service("joinSearchService")
public class JoinSearchServiceImpl implements IUserService{
	private final static int EMPTY_STRING=2;
	
	@Autowired
	private UserDAO dao;
	
	@Override
	public ResultDTO execute(Model model) {
		// TODO Auto-generated method stub
		String email=model.asMap().get("emailInfo").toString();
		System.out.println(email);
		ResultDTO result=null;
		if(!email.isEmpty()) //공백이 아닌 경우
			result=ResultDTO.createInstance(true).setData(dao.searchEmail(email));
		else result=ResultDTO.createInstance(false).setData(EMPTY_STRING); //공백인 경우
		return result;
	}

}
