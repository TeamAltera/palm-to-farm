package com.spring.smart_plant.commands;

import java.util.Map;

import org.springframework.ui.Model;

public interface UserRequestCommand {
	public Map<String, Object> execute(Model model);
}
