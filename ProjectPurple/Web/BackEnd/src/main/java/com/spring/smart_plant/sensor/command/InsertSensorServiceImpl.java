package com.spring.smart_plant.sensor.command;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.sensor.dao.SensorDAO;
import com.spring.smart_plant.sensor.domain.SensorDataDTO;

@Service("insertSensorService")
public class InsertSensorServiceImpl implements ISensorService{

	@Autowired
	private SensorDAO dao;
	
	@Override
	public void execute(SensorDataDTO dataSet) {
		// TODO Auto-generated method stub
	}

	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		HashMap<String, Object> income=(HashMap<String, Object>)obj;
		dao.insertData(income);
		return null;
	}

}
