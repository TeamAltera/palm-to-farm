package com.spring.smart_plant.user.command;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.user.dao.UserDAO;
import com.spring.smart_plant.user.domain.LoginDTO;
import com.spring.smart_plant.user.domain.UserInfoDTO;

@Service("signinService")
public class SigninServiceImpl implements IUserService {
	private final String KEY = "member";

	@Autowired
	private UserDAO dao;

	@Override
	public ResultDTO execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		LoginDTO loginInfo = (LoginDTO) map.get("loginInfo");
		UserInfoDTO dto = dao.searchMember(loginInfo); // 사용자가 존재하는지
		ResultDTO result = null;

		if (dto != null) {// 로그인 email, pwd가 일치한다면
			if (dto.getBlock() < 5) {// 계정 로그인 실패횟수가 5회가 아니라면

				dao.initBlockCount(dto.getUserCode());// 블락 횟수 초기화
				dto.setBlock(0);

				dto.setPwd(null);
				final String authToken = ConstantJwtService.getJwtService().create(KEY, dto, "user"); // 토큰 생성
				// ((HttpServletResponse) map.get("response")).setHeader(AUTH_HEADER, token);
				// 헤더에 토큰 포함
				result = ResultDTO.createInstance(true).setMsg("로그인 성공").setData(new Object() {
					public String token = authToken;
				});
			} else {
				result = ResultDTO.createInstance(false).setMsg("차단된 계정입니다.");
			}
		} else {// 로그인 email, pwd가 일치하지 않는 경우
			try {
				if (dao.searchBlock(loginInfo.getEmail()) == 5) {
					result = ResultDTO.createInstance(false).setMsg("차단된 계정입니다.").setData(null);
				} else {
					final String number = dao.incrementBlockCount(loginInfo.getEmail()).toString();
					result = ResultDTO.createInstance(false).setMsg("이메일 또는 패스워드가 불일치 합니다.")
							.setData(new Object() {
						@SuppressWarnings("unused")
						public String blockCount = number;
					});
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				result = ResultDTO.createInstance(false).setMsg("이메일 또는 패스워드가 불일치 합니다.").setData(null);
			}
		}
		return result;
	}
}
