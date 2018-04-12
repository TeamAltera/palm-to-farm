package com.spring.smart_plant.commands;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.spring.smart_plant.DAO.DAO;

public class DeleteDeviceCommand implements AjaxCommand{

	@Override
	public Map<String, Object> execute(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		int sfCode=(int)request.getAttribute("sfCode");
		DAO dao=new DAO();
		dao.deleteSmartFarmDevice(sfCode);
		
		return map;
	}

}
