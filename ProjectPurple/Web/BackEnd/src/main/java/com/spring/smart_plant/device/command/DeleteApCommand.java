package com.spring.smart_plant.device.command;

import com.spring.smart_plant.common.dao.DAO;
import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;

public class DeleteApCommand implements DeviceCommand{
	private final String PHP_DELETE_URL = "/delete.php";
	
	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		String apPublicIp=(String)obj;
		int userCode=(int)ConstantJwtService.getJwtService().get("member").get("userCode");
		
		UrlConnectionCommand cmd=new UrlConnectionCommand();
		//cmd.request(ip, urlPath, reqMethod, sendData);
		DAO dao=new DAO();
		dao.deleteAP(apPublicIp);
		int count=dao.deleteSmartFarmDevice(apPublicIp, userCode);
		return ResultDTO.createInstance(true).setMsg("공유기 한 대랑 수경재배기 "+count+"대가 삭제 되었습니다.")
				.setData(new Object() {
					private int deleteSfCnt=count;

					public int getDeleteSfCnt() {
						return deleteSfCnt;
					}
				});
	}
}
