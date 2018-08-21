package com.spring.smart_plant.sensor.command;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.sensor.dao.SensorDAO;
import com.spring.smart_plant.sensor.domain.SensorDataDTO;

public class DeleteSensorDataServiceImpl implements ISensorService{

	@Autowired
	SensorDAO dao;
	
	@Override
	public void execute(SensorDataDTO dataSet) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		try {
			dao.deleteAllSensorData((HashMap<String, Integer>)obj);
			return ResultDTO.createInstance(true).setMsg("센싱 데이터 삭제 성공");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultDTO.createInstance(false).setMsg("센싱 데이터 삭제 실패");
		}
	}
}
