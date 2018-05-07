package com.spring.smart_plant.sensor.command;

import com.spring.smart_plant.common.dao.DAO;
import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.sensor.domain.SensorDataDTO;

public class InsertCommand implements SensorCommand{

	private DAO dao=new DAO();
	
	@Override
	public void execute(SensorDataDTO dataSet) {
		// TODO Auto-generated method stub
		dao.insertData(dataSet);
	}

	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
