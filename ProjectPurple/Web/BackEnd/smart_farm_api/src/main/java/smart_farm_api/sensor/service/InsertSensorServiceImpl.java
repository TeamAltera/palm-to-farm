package smart_farm_api.sensor.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.sensor.domain.SensorDataDto;
import smart_farm_api.sensor.repository.SensorMapper;

@Service
@AllArgsConstructor
public class InsertSensorServiceImpl implements ISensorService{

	private SensorMapper sensorMapper;
	
	@Override
	public void execute(SensorDataDto dataSet) {
		// TODO Auto-generated method stub
	}

	@Override
	public ResultDto execute(Object obj) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		HashMap<String, Object> income=(HashMap<String, Object>)obj;
		sensorMapper.insertData(income);
		return null;
	}

}
