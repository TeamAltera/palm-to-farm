package com.spring.smart_plant.device.command;

import com.spring.smart_plant.DAO.DAO;
import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.device.domain.SFCodeDTO;

public class DeleteDeviceCommand implements DeviceCommand{

	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		DAO dao=new DAO();
		dao.deleteSmartFarmDevice(((SFCodeDTO)obj).getSfCode());
		return ResultDTO.createInstance(true);
	}

}
