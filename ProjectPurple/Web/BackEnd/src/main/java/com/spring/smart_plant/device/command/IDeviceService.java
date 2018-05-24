package com.spring.smart_plant.device.command;

import com.spring.smart_plant.common.domain.ResultDTO;

public interface IDeviceService {
	public ResultDTO execute(Object obj);
}
