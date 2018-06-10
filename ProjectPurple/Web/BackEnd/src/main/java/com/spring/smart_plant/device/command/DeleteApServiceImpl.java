package com.spring.smart_plant.device.command;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.device.dao.DeviceDAO;
import com.spring.smart_plant.device.domain.IpDTO;
import com.spring.smart_plant.log.dao.LogDAO;
import com.spring.smart_plant.user.dao.UserDAO;

@Service("deleteApService")
public class DeleteApServiceImpl implements IDeviceFrontService {
	private final String PHP_DELETE_URL = "/remove_pro_sys.php";

	@Autowired
	private DeviceDAO deviceDao;
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private LogDAO logDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = { Exception.class }) // 이 메소드를 트랜잭션 처리
	public ResultDTO execute(Object obj) throws Exception {
		// TODO Auto-generated method stub
		String apPublicIp = ((IpDTO) obj).getIp();
		System.out.println(apPublicIp);
		int userCode = (int) ConstantJwtService.getJwtService().get("member").get("userCode");

		UrlConnectionCommand cmd = new UrlConnectionCommand();
		try {
			JSONObject json = cmd.request(apPublicIp, PHP_DELETE_URL, "GET", null);
			if (json.get("result").equals("OK")) {
				int count;
				try {
					//SF테이블과, AP테이블을 사용한 delete join을 수행하므로
					//다른 쿼리를 날리는 작업보다 우선하여 로그 삭제 작업은 제일 위에 위치한다.
					logDao.deleteAllLog(apPublicIp); 
					
					deviceDao.deleteAP(apPublicIp);
					count = deviceDao.deleteSmartFarmAPAllDevice(apPublicIp,userCode);
					userDao.decrementSfCount(count, userCode);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw e;
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
