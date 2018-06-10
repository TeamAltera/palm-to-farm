package com.spring.smart_plant.device.command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.device.dao.DeviceDAO;
import com.spring.smart_plant.device.domain.APInfoDTO;
import com.spring.smart_plant.device.domain.SmartFarmInfoDTO;

@Service("getDeviceService")
public class GetDeviceServiceImpl implements IDeviceFrontService{

	@Autowired
	private DeviceDAO dao;
	
	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		int userCode=(int)ConstantJwtService.getJwtService().get("member").get("userCode");
		List<SmartFarmInfoDTO> plantDevices=dao.getAllSmartPlant(userCode);
		List<APInfoDTO> raspAPDevices =dao.getAllAP(userCode);
		return ResultDTO.createInstance(true)
				.setMsg("계정에 등록되어진 공유기, 수경재배기 정보")
				.setData(null);
	}

}
