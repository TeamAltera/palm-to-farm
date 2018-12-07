package smart_farm_api.sensor.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.DateSearchDto;
import smart_farm_api.common.ResultDto;
import smart_farm_api.sensor.domain.SensorDataDto;
import smart_farm_api.sensor.repository.SensorMapper;

@Service
@AllArgsConstructor
public class GetSensorLastDataService implements ISensorService {

	private SensorMapper sensorMapper;
	
	@Override
	public void execute(SensorDataDto dataSet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResultDto execute(Object obj) {
		// TODO Auto-generated method stub
		DateSearchDto dto=(DateSearchDto)obj;
		return ResultDto.createInstance(true).setData(sensorMapper.getLastData(dto));
	}

}
