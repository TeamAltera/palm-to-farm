package com.spring.smart_plant.device.command;

import com.spring.smart_plant.DAO.DAO;
import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.device.domain.APInfoDTO;
import com.spring.smart_plant.device.domain.DeviceInfoDTO;
import com.spring.smart_plant.user.domain.UserInfoDTO;

public class AddDeviceCommand implements DeviceCommand {

	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		DeviceInfoDTO dto = (DeviceInfoDTO)obj;
		DAO dao = new DAO();
		if (dto.getIp() != null)
			dao.insertAP(new APInfoDTO(dto.getIp(), dto.getSsid()));
		if (dto.getInnerIp() != null) {
			for (String innerIp : dto.getInnerIp()) {
				System.out.println(dto.getInnerIp());
				dao.insertSmartFarmDevice(dto.getIp(), innerIp, ((UserInfoDTO) obj).getUserCode());
			}
		}
		return ResultDTO.createInstance(true);
	}
}
