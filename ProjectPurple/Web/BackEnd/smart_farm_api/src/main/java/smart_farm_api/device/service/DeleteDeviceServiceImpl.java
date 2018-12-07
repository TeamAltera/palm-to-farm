package smart_farm_api.device.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.device.domain.CommandDto;
import smart_farm_api.device.domain.SmartFarmInfoDto;
import smart_farm_api.device.repository.DeviceMapper;

@Service
@AllArgsConstructor
public class DeleteDeviceServiceImpl implements IDeviceFrontService{
	private final String PHP_DELETE_URL = "/remove_pro.php";
	
	private DeviceMapper deviceMapper;
	
	private UrlConnectionServiceImpl urlConnectionService;
	
	private final int CMD=15;
	
	@Override
	public ResultDto execute(Object obj) {
		CommandDto commandSet=(CommandDto)obj;
		int cmd=commandSet.getCmd();
		String requestData="{\"cmd\":\"" + CMD 
				+ "\",\"dest\":\""+commandSet.getDest()
				+ "\",\"stamp\":\""+commandSet.getApCode()+ "\"}";
		
		try {
			JSONObject json = urlConnectionService.request(commandSet.getMiddle(), PHP_DELETE_URL, "POST", requestData);
			final String result=(String)json.get("result");
			
			//재배기 제거 명령이 성공했다면
			if(!result.equals("err")) { 
				deviceMapper.deleteSmartFarmDevice(commandSet.getStamp());
				return ResultDto.createInstance(true).setMsg("명령이 정상적으로 수행되었습니다.").setData(
						new Object() {
							public String res=result;
							public SmartFarmInfoDto deviceInfo=(SmartFarmInfoDto) deviceMapper.getSmartPlant(commandSet.getApCode(),commandSet.getStamp());
						}
				);
			}
			//명령이 실패했다면
			else {
				return ResultDto.createInstance(false).setMsg("명령이 실패했습니다.").setData(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultDto.createInstance(false).setMsg("명령이 실패했습니다.");
	}

}
