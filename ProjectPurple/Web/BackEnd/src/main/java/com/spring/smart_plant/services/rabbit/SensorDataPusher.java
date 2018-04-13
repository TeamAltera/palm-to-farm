package com.spring.smart_plant.services.rabbit;

import com.spring.smart_plant.DAO.DAO;
import com.spring.smart_plant.DTO.SensorDataDTO;

public class SensorDataPusher {
	
	public void insertSensorData(SensorDataDTO dto) {
		DAO dao=new DAO();
		//dao.insertData(dto);
	}
}
