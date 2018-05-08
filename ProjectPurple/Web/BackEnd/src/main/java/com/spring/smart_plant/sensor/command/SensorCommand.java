package com.spring.smart_plant.sensor.command;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.sensor.domain.SensorDataDTO;

public interface SensorCommand {
	public void execute(SensorDataDTO dataSet);
	
	public ResultDTO execute(Object obj);
}
