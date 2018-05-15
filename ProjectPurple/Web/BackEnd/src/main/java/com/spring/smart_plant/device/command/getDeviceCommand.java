package com.spring.smart_plant.device.command;

import java.util.ArrayList;
import java.util.List;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.device.dao.DeviceDAO;
import com.spring.smart_plant.device.domain.APInfoDTO;
import com.spring.smart_plant.device.domain.SmartFarmInfoDTO;

public class getDeviceCommand implements IDeviceCommand{

	@Override
	public ResultDTO execute(Object obj,DeviceDAO dao) {
		// TODO Auto-generated method stub
		int userCode=(int)ConstantJwtService.getJwtService().get("member").get("userCode");
		
		
		return ResultDTO.createInstance(true)
				.setMsg("계정에 등록되어진 공유기, 수경재배기 정보")
				.setData(new Object(){
			private List<SmartFarmInfoDTO> plantDevices=dao.getAllSmartPlant(userCode);
			private List<APInfoDTO> raspAPDevices =dao.getAllAP(userCode);
			
			public List<SmartFarmInfoDTO> getPlantDevices() {
				return plantDevices;
			}
			public List<APInfoDTO> getRaspAPDevices() {
				return raspAPDevices;
			}
		});//모든 수경재배기, 공유기 정보 조회 결과
	}

}
