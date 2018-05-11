package com.spring.smart_plant.device.command;

import java.io.IOException;

import org.json.JSONObject;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.device.dao.DeviceDAO;
import com.spring.smart_plant.device.domain.IpDTO;

public class DeleteApCommand implements IDeviceCommand {
	private final String PHP_DELETE_URL = "/remove_pro_sys.php";

	@Override
	public ResultDTO execute(Object obj,DeviceDAO dao) {
		// TODO Auto-generated method stub
		String apPublicIp = ((IpDTO) obj).getInnerIp();
		//int userCode = (int) ConstantJwtService.getJwtService().get("member").get("userCode");

		UrlConnectionCommand cmd = new UrlConnectionCommand();
		try {
			JSONObject json = cmd.request(apPublicIp, PHP_DELETE_URL, "GET", null);
			if (json.get("result").equals("OK")) {
				
				dao.deleteAP(apPublicIp);
				int count = dao.deleteSmartFarmAPAllDevice(apPublicIp);
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
