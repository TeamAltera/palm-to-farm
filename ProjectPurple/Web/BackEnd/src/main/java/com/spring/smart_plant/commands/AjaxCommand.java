package com.spring.smart_plant.commands;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface AjaxCommand {
	public Map<String, Object> execute(HttpServletRequest request);
}
