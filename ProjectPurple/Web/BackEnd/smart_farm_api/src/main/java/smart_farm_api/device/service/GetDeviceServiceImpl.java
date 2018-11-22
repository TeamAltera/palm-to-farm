package smart_farm_api.device.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.common.service.JwtServiceImpl;
import smart_farm_api.device.domain.APInfoDto;
import smart_farm_api.device.domain.SmartFarmInfoDto;
import smart_farm_api.device.repository.DeviceMapper;

@Service
@AllArgsConstructor
public class GetDeviceServiceImpl implements IDeviceFrontService {

	private DeviceMapper deviceMapper;
	
	private JwtServiceImpl jwtService;

	@Override
	public ResultDto execute(Object obj) {
		// TODO Auto-generated method stub
		
		return ResultDto.createInstance(true).setMsg("계정에 등록되어진 공유기, 수경재배기 정보")
				.setData(selectDevices());
	}
	
	public Object selectDevices() {
		int userCode = jwtService.get();
		List<APInfoDto> raspAPDevices = deviceMapper.getAllAP(userCode);
		List<SmartFarmInfoDto> plantDevices = deviceMapper.getAllSmartPlant(userCode);
		for (APInfoDto apInfoDTO : raspAPDevices) {
			apInfoDTO.setPlantDevices(new ArrayList<>());
			for (SmartFarmInfoDto smartFarmInfoDTO : plantDevices) {
				if(apInfoDTO.getApCode()==smartFarmInfoDTO.getApCode()) {
					apInfoDTO.getPlantDevices().add(smartFarmInfoDTO);
				}
			}	
		}
		return raspAPDevices;
//		int userCode = (int) ConstantJwtService.getJwtService().get("member").get("userCode");
//		return new Object() {
//			private List<APInfoDTO> raspAPDevices = dao.getAllAP(userCode);
//			private List<SmartFarmInfoDTO> plantDevices = dao.getAllSmartPlant(userCode);
//			public List<APInfoDTO> getRaspAPDevices() {
//				return raspAPDevices;
//			}
//			public List<SmartFarmInfoDTO> getPlantDevices() {
//				return plantDevices;
//			}
//			
//		};
		
	}
}
