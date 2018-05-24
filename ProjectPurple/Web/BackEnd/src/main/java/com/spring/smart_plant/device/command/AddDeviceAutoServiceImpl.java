package com.spring.smart_plant.device.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.device.dao.DeviceDAO;
import com.spring.smart_plant.device.domain.DeviceInfoDTO;

@Service("addDeviceAutoService")
public class AddDeviceAutoServiceImpl implements IDeviceService{

	@Autowired
	private DeviceDAO dao;
	
	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		
		//라즈베리 공유기에도 토큰을 발급하는 식으로, 사용자가 아닌 기계에서 요청이 들어오는 작업또한 권한이 있어야
		//실행될 수 있는 식으로 수정해야
		
		DeviceInfoDTO deviceInfo=(DeviceInfoDTO)obj;
		String innerIp=deviceInfo.getIpInfo();
		int userCode=deviceInfo.getUserCode();
		String publicIp=deviceInfo.getApInfo();
		System.out.println(innerIp+","+userCode+","+publicIp);
		
		try {
			dao.insertSmartFarmDevice(innerIp, userCode, publicIp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResultDTO.createInstance(false).setMsg("수경재배기 추가 실패");
		}
		return ResultDTO.createInstance(true).setMsg("수경재배기 추가 완료");
	}

}
