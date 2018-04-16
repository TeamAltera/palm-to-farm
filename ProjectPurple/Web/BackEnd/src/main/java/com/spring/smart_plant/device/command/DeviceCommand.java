package com.spring.smart_plant.device.command;

import com.spring.smart_plant.common.domain.ResultDTO;

public interface DeviceCommand {
	public ResultDTO execute(Object obj);
}
