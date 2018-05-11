package com.spring.smart_plant.device.command;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.device.dao.DeviceDAO;

public interface IDeviceCommand {
	public ResultDTO execute(Object obj, DeviceDAO dao);
}
