package com.spring.smart_plant.user.command;

import org.springframework.ui.Model;

import com.spring.smart_plant.common.domain.ResultDTO;

public interface UserCommand {
	public ResultDTO execute(Model model);
}
