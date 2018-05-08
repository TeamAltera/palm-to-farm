package com.spring.smart_plant.device.command;

import com.spring.smart_plant.common.dao.DAO;
import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;

public class DeleteDeviceCommand implements DeviceCommand{
	private final String PHP_DELETE_URL = "/add.php";
	
	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		DAO dao=new DAO();
		int sfCode=(int)obj;
		int userCode=(int)ConstantJwtService.getJwtService().get("member").get("userCode");
		dao.deleteSmartFarmDevice(sfCode,userCode);
		String msg=sfCode+"번째 수경재배기가 정상적으로 삭제";
		return ResultDTO.createInstance(true).setMsg(msg);
	}

}
