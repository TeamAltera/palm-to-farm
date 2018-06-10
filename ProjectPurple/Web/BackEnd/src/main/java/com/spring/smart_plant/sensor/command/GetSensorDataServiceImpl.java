package com.spring.smart_plant.sensor.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.smart_plant.common.domain.DateSearchDTO;
import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.sensor.dao.SensorDAO;
import com.spring.smart_plant.sensor.domain.SensorDataDTO;

@Service("getSensorDataService")
public class GetSensorDataServiceImpl implements ISensorService {

	@Autowired
	private SensorDAO dao;
	
	@Override
	public void execute(SensorDataDTO dataSet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		DateSearchDTO dto=(DateSearchDTO)obj;
		return ResultDTO.createInstance(true).setData(dao.getDayData(dto));
	}

}
