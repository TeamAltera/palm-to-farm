package com.spring.smart_plant.device.command;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.device.dao.DeviceDAO;

public class DeleteDeviceCommand implements IDeviceCommand{
	private final String PHP_DELETE_URL = "/add.php";
	
	@Override
	public ResultDTO execute(Object obj,DeviceDAO dao) {
		// TODO Auto-generated method stub
		
		int sfCode=(int)obj;
		//int userCode=(int)ConstantJwtService.getJwtService().get("member").get("userCode");
		dao.deleteSmartFarmDevice(sfCode);
		String msg=sfCode+"번째 수경재배기가 정상적으로 삭제";
		return ResultDTO.createInstance(true).setMsg(msg);
	}

}
