package smart_farm_api.sensor.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import smart_farm_api.common.DateSearchDto;
import smart_farm_api.sensor.domain.SensorDataDto;

public interface SensorMapper {
	void insertData(HashMap<String, Object> map);
	
	SensorDataDto getLatestData(@Param("sfCode") int sfCode, @Param("apCode") int apCode);
	
	List<SensorDataDto> getDayData(DateSearchDto dto);
	
	void deleteAllSensorData(HashMap<String, Integer> map);
}
