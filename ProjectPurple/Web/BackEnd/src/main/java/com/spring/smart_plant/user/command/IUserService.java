package com.spring.smart_plant.user.command;

import org.springframework.ui.Model;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.user.dao.UserDAO;

public interface IUserService {
	public ResultDTO execute(Model model);
}
