package smart_farm_api.device.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.device.domain.DeviceInfoDto;
import smart_farm_api.device.repository.DeviceMapper;

@Service
@AllArgsConstructor
public class AddDeviceAutoServiceImpl implements IDeviceFrontService{

	private DeviceMapper deviceMapper;
	
	private SimpMessagingTemplate messagingTemplate;
	
	//private RabbitMessagingTemplate  messagingTemplate;
	
	@Override
	public ResultDto execute(Object obj) {
		// TODO Auto-generated method stub
		
		//라즈베리 공유기에도 토큰을 발급하는 식으로, 사용자가 아닌 기계에서 요청이 들어오는 작업또한 권한이 있어야
		//실행될 수 있는 식으로 수정해야
		DeviceInfoDto deviceInfo=(DeviceInfoDto)obj;
		String innerIp=deviceInfo.getIpInfo();
		int apCode=deviceInfo.getApCode();
		int stamp=deviceInfo.getStamp();
		int sfCode=deviceInfo.getSfCode();
		try {
			deviceMapper.insertSmartFarmDevice(stamp,sfCode,innerIp, apCode);//DB에 수경재배기 정보 자동으로 추가
			deviceMapper.updateSFCount(apCode); 
			int userCode = deviceMapper.getUserCodeOfAP(apCode);
			this.messagingTemplate.convertAndSend("/topic/us"+userCode,
					deviceMapper.getSmartPlant(apCode,stamp));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultDto.createInstance(false).setMsg("수경재배기 추가 실패");
		}
		return ResultDto.createInstance(true).setMsg("수경재배기 추가 성공");
	}
}
