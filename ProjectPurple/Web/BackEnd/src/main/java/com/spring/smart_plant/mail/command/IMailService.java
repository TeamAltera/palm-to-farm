package com.spring.smart_plant.mail.command;

import javax.servlet.http.HttpSession;

import com.spring.smart_plant.common.domain.ResultDTO;

public interface IMailService {
	public ResultDTO execute(Object obj, HttpSession session);
}
