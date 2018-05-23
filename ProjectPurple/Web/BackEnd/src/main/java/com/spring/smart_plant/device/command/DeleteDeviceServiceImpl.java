package com.spring.smart_plant.device.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.device.dao.DeviceDAO;

@Service("deleteDeviceService")
public class DeleteDeviceServiceImpl implements IDeviceService{
	private final String PHP_DELETE_URL = "/add.php";
	
	@Autowired
	private DeviceDAO dao;
	
	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		
		int sfCode=(int)obj;
		//int userCode=(int)ConstantJwtService.getJwtService().get("member").get("userCode");
		dao.deleteSmartFarmDevice(sfCode);
		String msg=sfCode+"번째 수경재배기가 정상적으로 삭제";
		return ResultDTO.createInstance(true).setMsg(msg);
	}

}
