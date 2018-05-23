package com.spring.smart_plant.device.command;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.device.dao.DeviceDAO;
import com.spring.smart_plant.device.domain.IpDTO;

@Service("deleteApService")
public class DeleteApServiceImpl implements IDeviceService {
	private final String PHP_DELETE_URL = "/remove_pro_sys.php";

	@Autowired
	private DeviceDAO dao;
	
	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		String apPublicIp = ((IpDTO) obj).getIp();
		int userCode = (int) ConstantJwtService.getJwtService().get("member").get("userCode");

		UrlConnectionCommand cmd = new UrlConnectionCommand();
		try {
			JSONObject json = cmd.request(apPublicIp, PHP_DELETE_URL, "GET", null);
			if (json.get("result").equals("OK")) {
				
				dao.deleteAP(apPublicIp);
				int count;
				try {
					count = dao.deleteSmartFarmAPAllDevice(apPublicIp,userCode);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return ResultDTO.createInstance(false).setMsg("삭제가 실패하였습니다.");
				}
				return ResultDTO.createInstance(true).setMsg("공유기 한 대랑 수경재배기 " + count + "대가 삭제 되었습니다.")
						.setData(new Object() {
							private int deleteSfCnt = count;

							@SuppressWarnings("unused")
							public int getDeleteSfCnt() {
								return deleteSfCnt;
							}
						});
			}
			else {
				return ResultDTO.createInstance(false).setMsg("명령이 실패했습니다.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultDTO.createInstance(false).setMsg("명령이 실패했습니다.");
		}
	}
}
