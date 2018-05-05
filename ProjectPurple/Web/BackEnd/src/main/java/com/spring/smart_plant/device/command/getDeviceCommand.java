package com.spring.smart_plant.device.command;

import java.util.ArrayList;

import com.spring.smart_plant.DAO.DAO;
import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.device.domain.APInfoDTO;
import com.spring.smart_plant.device.domain.SmartFarmInfoDTO;

public class getDeviceCommand implements DeviceCommand{

	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		int userCode=(int)ConstantJwtService.getJwtService().get("member").get("userCode");
		
		DAO dao=new DAO();
		return ResultDTO.createInstance(true).setMsg("계정에 등록되어진 공유기, 수경재배기 정보").setData(new Object(){
			private ArrayList<SmartFarmInfoDTO> plantDevices=dao.getAllSmartPlant(userCode);
			private ArrayList<APInfoDTO> raspAPDevices =dao.getAllAP(userCode);
			
			public ArrayList<SmartFarmInfoDTO> getPlantDevices() {
				return plantDevices;
			}

			public ArrayList<APInfoDTO> getRaspAPDevices() {
				return raspAPDevices;
			}
			
		});//모든 수경재배기, 공유기 정보 조회 결과
	}

}
