package com.spring.smart_plant.log.command;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.log.dao.LogDAO;

public interface ILogService {
	public ResultDTO execute(Object obj);
}
