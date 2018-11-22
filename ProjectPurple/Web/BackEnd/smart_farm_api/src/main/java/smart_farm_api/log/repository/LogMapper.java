package smart_farm_api.log.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import smart_farm_api.common.DateSearchDto;
import smart_farm_api.log.domain.DeviceLogDto;

public interface LogMapper {
	void insertLog(DeviceLogDto dto);
	
	List<DeviceLogDto> getLog(DateSearchDto dto);
	
	void deleteAllLog(int apCode);
	
	int deleteSingleLog(@Param("sfCode") int sfCode,@Param("apCode") int apCode);
}
