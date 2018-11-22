package smart_farm_api.device.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.device.repository.DeviceMapper;

@Service
@AllArgsConstructor
public class DeleteDeviceServiceImpl implements IDeviceFrontService{
	private final String PHP_DELETE_URL = "/add.php";
	
	private DeviceMapper deviceMapper;
	
	@Override
	public ResultDto execute(Object obj) {
		// TODO Auto-generated method stub
		
		int sfCode=(int)obj;
		//int userCode=(int)ConstantJwtService.getJwtService().get("member").get("userCode");
		deviceMapper.deleteSmartFarmDevice(sfCode);
		String msg=sfCode+"번째 수경재배기가 정상적으로 삭제";
		return ResultDto.createInstance(true).setMsg(msg);
	}

}
