package com.spring.smart_plant.commands;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.spring.smart_plant.DAO.DAO;
import com.spring.smart_plant.DTO.APInfoDTO;
import com.spring.smart_plant.DTO.UserInfoDTO;
import com.spring.smart_plant.DTO.JSON.DeviceInfoJSON;

public class AddDeviceCommand implements AjaxCommand {

	@Override
	public Map<String, Object> execute(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession(false);
		Object obj = session.getAttribute("userInfo");
		if (session != null && obj != null) {
			DeviceInfoJSON dto = (DeviceInfoJSON) request.getAttribute("deviceInfo");
			DAO dao = new DAO();
			if (dto.getIp() != null)
				dao.insertAP(new APInfoDTO(dto.getIp(), dto.getSsid()));
			if (dto.getInnerIp() != null) {
				for (String innerIp : dto.getInnerIp()) {
					System.out.println(dto.getInnerIp());
					dao.insertSmartFarmDevice(dto.getIp(), innerIp, ((UserInfoDTO)obj).getUserCode());
				}
			}
		}
		return map;
	}

}
