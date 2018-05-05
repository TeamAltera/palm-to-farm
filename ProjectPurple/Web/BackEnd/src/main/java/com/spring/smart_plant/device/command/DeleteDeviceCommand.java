package com.spring.smart_plant.device.command;

import com.spring.smart_plant.DAO.DAO;
import com.spring.smart_plant.common.domain.ResultDTO;

public class DeleteDeviceCommand implements DeviceCommand{

	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		DAO dao=new DAO();
		int sfCode=(int)obj;
		dao.deleteSmartFarmDevice(sfCode);
		String msg=sfCode+"번째 수경재배기가 정상적으로 삭제";
		return ResultDTO.createInstance(true).setMsg(msg);
	}

}
