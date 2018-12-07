package smart_farm_api.device.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.device.domain.APStatusDto;
import smart_farm_api.device.repository.DeviceMapper;

@Service
@AllArgsConstructor
public class ConfirmAPServiceImpl implements IDeviceFrontService {
	private final String PHP_SEARCH_URL = "/search.php";
	private final String SUCCESS_MSG="사용가능한 라우터 입니다.";
	private final String FAIL_MSG="이미 사용중인 라우터 입니다.";
	private final String UNKNOWN_MSG="존재하지 않는 라우터 입니다.";
	private final int SUCCESS_CODE=200;//성공한 경우
	private final int FAIL_CODE=201;//실패한 경우
	private final int UNKNOWN_CODE=202;//pending, 존재하지 않는 공유기인 경우
	
	private UrlConnectionServiceImpl urlConnectionService;
	
	private DeviceMapper deviceMapper;
	
	@Override
	public ResultDto execute(Object obj) {
		// TODO Auto-generated method stub
		String ip = (String) obj;
		String msg=null;
		APStatusDto code=APStatusDto.builder().code(FAIL_CODE).build();
		boolean httpStatus=true;
		if (deviceMapper.getAP(ip)==null){//AP가 존재하지 않는다면 미들서버로부터 정보를 받아온다.
			try {
				JSONObject json=urlConnectionService.request(ip, PHP_SEARCH_URL, "POST",null);
	            if(json.get("state").equals("FAIL")) {//이미 사용중
	            	msg=FAIL_MSG;
	            }
	            else { //사용가능한 경우
	            	code.setCode(SUCCESS_CODE);
	            	msg=SUCCESS_MSG;
	            	//연결된 수경재배기 IP들을 가져옴
	            	JSONArray arr=(JSONArray)json.get("inner_ip");
	            	
	            	List<Object> list=new ArrayList<>();
	            	for(int i=0; i< arr.length(); i++) {
	            		list.add(arr.getJSONObject(i).getString("INNER_IP"));
	            	}
	            	code.setInnerIp(list);
	            }
			} catch (IOException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msg=UNKNOWN_MSG;
				code.setCode(UNKNOWN_CODE);
				httpStatus=false;
			}
		}
		else {
			msg=FAIL_MSG;
		}
		return ResultDto.createInstance(httpStatus).setMsg(msg).setData(code);
	}
}
