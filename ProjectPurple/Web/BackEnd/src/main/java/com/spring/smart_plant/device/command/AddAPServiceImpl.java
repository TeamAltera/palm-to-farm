package com.spring.smart_plant.device.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.device.dao.DeviceDAO;
import com.spring.smart_plant.device.domain.APInfoDTO;
import com.spring.smart_plant.user.dao.UserDAO;

@Service("addAPService")
public class AddAPServiceImpl implements IDeviceFrontService {
	private final String PHP_SEARCH_URL = "/search.php";
	private final String PHP_ADD_URL = "/add.php";

	@Autowired
	private DeviceDAO deviceDao;

	@Autowired
	private UserDAO userDao;

	@Autowired
	UrlConnectionServiceImpl urlConnectionService;
	
	@Autowired
	private GetDeviceServiceImpl deviceService;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class }) // 이 메소드를 트랜잭션 처리
	@Override
	public ResultDTO execute(Object obj) throws Exception, IOException {
		// TODO Auto-generated method stub
		String publicIP = (String) obj;
		try {
			JSONObject json = urlConnectionService.request(publicIP, PHP_SEARCH_URL, "POST", null);
			System.out.println(json.get("state"));
			if (json.get("state").equals("OK")) {// 사용가능한 공유기인 경우
				System.out.println("insert");
				// JWT로 부터 usercode를 알아냄
				int userCode = (int) ConstantJwtService.getJwtService().get("member").get("userCode");

				// DB에 공유기정보 삽입, 삽입 후 공유기 코드(apCode) 받아옴.
				Integer apCode = deviceDao.insertAP(new APInfoDTO(publicIP, (String) json.get("ssid"), userCode));

				JSONArray arr = (JSONArray) json.get("inner_ip");
				List<Map<String, Object>> deviceList = new ArrayList<Map<String, Object>>();
				int count=arr.length();
				
				for (int i = 0; i < count; i++) {//List에다 map요소 추가
					JSONObject item=arr.getJSONObject(i);
					Map<String, Object> map = new HashMap<String, Object>();
					String innerIp=(String) item.get("INNER_IP");
					map.put("innerIp", innerIp);
					map.put("sfCode", innerIp.substring(innerIp.lastIndexOf('.')+1, innerIp.length()));
					map.put("sfPortCnt", 32);
					map.put("floorCnt", 2);
					map.put("coolerCnt", 3);
					map.put("mode", 'A');
					map.put("ledSt", 'F');
					map.put("pumpSt", 'F');
					map.put("coolerSt", 'F');
					deviceList.add(map);
				}
				
				if(count!=0) {
					deviceDao.insertSmartFarmDeviceList(deviceList, apCode); // DB에 수경재배기 정보들 삽입
					userDao.incrementSfCount(count, userCode);// 사용자 수경재배기 갯수 증가
				}

				String msg = "공유기 1대와 수경재배기 " + count + "대가 등록되었습니다.";

				// add.php로 apCode를 전송
				String reqMsg = 
					"{\"ap_code\":\"" + apCode.toString()
					+"\",\"public_ip\":\""+publicIP
					+ "\"}";
				urlConnectionService.request(publicIP, PHP_ADD_URL, "POST", reqMsg);

				return ResultDTO.createInstance(true).setMsg(msg).setData(new Object() {
					private Object deviceInfo=deviceService.selectDevices();

					public Object getDeviceInfo() {
						return deviceInfo;
					}
				});//상태 값 반환
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return ResultDTO.createInstance(false).setMsg("등록 오류입니다.");
	}
}
