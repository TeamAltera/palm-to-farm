package smart_farm_api.sensor.service;

import smart_farm_api.common.ResultDto;
import smart_farm_api.sensor.domain.SensorDataDto;

public interface ISensorService {
	public void execute(SensorDataDto dataSet);
	
	public ResultDto execute(Object obj);
}
