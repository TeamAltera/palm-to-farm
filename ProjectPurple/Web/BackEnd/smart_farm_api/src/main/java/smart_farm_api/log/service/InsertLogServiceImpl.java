package smart_farm_api.log.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.log.domain.DeviceLogDto;
import smart_farm_api.log.repository.LogMapper;

@Service
@AllArgsConstructor
public class InsertLogServiceImpl implements ILogService{
	
	private LogMapper logMapper;
	
	@Override
	public ResultDto execute(Object obj) {
		logMapper.insertLog((DeviceLogDto)obj);//Y이면 성공 N이면 실패
		return null;
	}
}
