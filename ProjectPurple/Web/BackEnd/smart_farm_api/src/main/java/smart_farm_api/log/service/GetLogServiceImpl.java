package smart_farm_api.log.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.DateSearchDto;
import smart_farm_api.common.ResultDto;
import smart_farm_api.log.domain.DeviceLogDto;
import smart_farm_api.log.repository.LogMapper;

@Service
@AllArgsConstructor
public class GetLogServiceImpl implements ILogService{

	private LogMapper logMapper;
	
	@Override
	public ResultDto execute(Object obj) {
		// TODO Auto-generated method stub
		DateSearchDto dto=(DateSearchDto)obj;
		List<DeviceLogDto> result=logMapper.getLog(dto);
		return ResultDto.createInstance(true)
				.setMsg(dto.getDate()+"의 사용기록은 "+result.size()+"개 입니다.")
				.setData(result);
	}
}
