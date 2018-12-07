package smart_farm_api.device.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.device.domain.ChangeSfDto;
import smart_farm_api.device.repository.DeviceMapper;

@Service
@AllArgsConstructor
public class ChangeDeviceServiceImpl implements IDeviceFrontService{

	private DeviceMapper deviceMapper;
	
	@Override
	public ResultDto execute(Object obj){
		// TODO Auto-generated method stub
		ChangeSfDto dto=(ChangeSfDto)obj;
		deviceMapper.changeInnerIp(dto.getBefore(), dto.getAfter(), "192.168.4."+dto.getAfter(),
				dto.getStamp(), dto.getApCode());
		return ResultDto.createInstance(true);
	}

}
