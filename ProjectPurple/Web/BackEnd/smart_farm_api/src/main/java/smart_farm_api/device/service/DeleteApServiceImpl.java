package smart_farm_api.device.service;

import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.common.utils.JwtServiceImpl;
import smart_farm_api.device.repository.DeviceMapper;
import smart_farm_api.log.repository.LogMapper;
import smart_farm_api.user.repository.UserMapper;

@Service
@AllArgsConstructor
public class DeleteApServiceImpl implements IDeviceFrontService {
	private final String PHP_DELETE_URL = "/remove_pro_sys.php";

	private DeviceMapper deviceMapper;

	private UserMapper userMapper;

	private LogMapper logMapper;

	private GetDeviceServiceImpl deviceService;
	
	private JwtServiceImpl jwtService;
	
	private UrlConnectionServiceImpl urlConnectionService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class }) // 이 메소드를 트랜잭션 처리
	public ResultDto execute(Object obj) throws Exception {
		// TODO Auto-generated method stub
		int apCode = (int) obj;
		int userCode = jwtService.get();
		try {
			JSONObject json = urlConnectionService.request(deviceMapper.getApIp(apCode), PHP_DELETE_URL, "GET", null);
			if (json.get("result").equals("OK")) {
				int count;
				// SF테이블과, AP테이블을 사용한 delete join을 수행하므로
				// 다른 쿼리를 날리는 작업보다 우선하여 로그 삭제 작업은 제일 위에 위치한다.
				logMapper.deleteAllLog(apCode);
				// 참조 무결성 때문에 SF삭제후 AP삭제를 진행한다.
				count = deviceMapper.deleteSmartFarmAPAllDevice(apCode);
				deviceMapper.deleteAP(apCode);
				userMapper.decrementSfCount(count, userCode);

				return ResultDto.createInstance(true).setMsg("공유기 한 대랑 수경재배기 " + count + "대가 삭제 되었습니다.")
						.setData(new Object() {
							public int deleteSfCnt = count;
							public Object deviceInfo = deviceService.selectDevices();
						});
			} else {
				return ResultDto.createInstance(false).setMsg("명령이 실패했습니다.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultDto.createInstance(false).setMsg("명령이 실패했습니다.");
		}
	}
}
