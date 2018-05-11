package com.spring.smart_plant.log.command;

import com.spring.smart_plant.common.domain.ResultDTO;

public interface ILogCommand {
	public ResultDTO execute(int sfCode);
}
