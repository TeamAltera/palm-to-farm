package com.spring.smart_plant.plant.command;

import com.spring.smart_plant.common.domain.ResultDTO;

public interface IPlantService {
	public ResultDTO execute(Object obj) throws Exception;
}
