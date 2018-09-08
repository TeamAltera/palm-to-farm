package com.spring.smart_plant.device.command;

import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.device.dao.DeviceDAO;
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

	@Autowired
	private GetDeviceServiceImpl deviceService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class }) // 이 메소드를 트랜잭션 처리
	public ResultDTO execute(Object obj) throws Exception {
		// TODO Auto-generated method stub
		int apCode = (int) obj;
		System.out.println(apCode);
		int userCode = (int) ConstantJwtService.getJwtService().get("member").get("userCode");
		UrlConnectionServiceImpl cmd = new UrlConnectionServiceImpl();
		try {
			JSONObject json = cmd.request(deviceDao.getApIp(apCode), PHP_DELETE_URL, "GET", null);
			if (json.get("result").equals("OK")) {
				int count;
				// SF테이블과, AP테이블을 사용한 delete join을 수행하므로
				// 다른 쿼리를 날리는 작업보다 우선하여 로그 삭제 작업은 제일 위에 위치한다.
				logDao.deleteAllLog(apCode);
				// 참조 무결성 때문에 SF삭제후 AP삭제를 진행한다.
				count = deviceDao.deleteSmartFarmAPAllDevice(apCode);
				deviceDao.deleteAP(apCode);
				userDao.decrementSfCount(count, userCode);

				return ResultDTO.createInstance(true).setMsg("공유기 한 대랑 수경재배기 " + count + "대가 삭제 되었습니다.")
						.setData(new Object() {
							private int deleteSfCnt = count;
							private Object deviceInfo = deviceService.selectDevices();

							public int getDeleteSfCnt() {
								return deleteSfCnt;
							}

							public Object getDeviceInfo() {
								return deviceInfo;
							}

						});
			} else {
				return ResultDTO.createInstance(false).setMsg("명령이 실패했습니다.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultDTO.createInstance(false).setMsg("명령이 실패했습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultDTO.createInstance(false).setMsg("수경재배기의 작업을 우선적으로 취소하십시오.");
		}
	}
}
