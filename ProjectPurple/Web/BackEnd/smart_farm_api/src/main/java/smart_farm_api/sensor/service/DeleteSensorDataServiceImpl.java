package smart_farm_api.sensor.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.sensor.domain.SensorDataDto;
import smart_farm_api.sensor.repository.SensorMapper;

@Service
@AllArgsConstructor
public class DeleteSensorDataServiceImpl implements ISensorService{

	private SensorMapper sensorMapper;
	
	@Override
	public void execute(SensorDataDto dataSet) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultDto execute(Object obj) {
		// TODO Auto-generated method stub
		try {
			sensorMapper.deleteAllSensorData((HashMap<String, Integer>)obj);
			return ResultDto.createInstance(true).setMsg("센싱 데이터 삭제 성공");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultDto.createInstance(false).setMsg("센싱 데이터 삭제 실패");
		}
	}
}
