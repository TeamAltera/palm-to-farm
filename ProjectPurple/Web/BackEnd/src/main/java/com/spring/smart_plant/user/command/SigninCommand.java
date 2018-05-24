package com.spring.smart_plant.user.command;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.user.dao.UserDAO;
import com.spring.smart_plant.user.domain.LoginDTO;
import com.spring.smart_plant.user.domain.UserInfoDTO;

public class SigninCommand implements IUserCommand {
	private final String AUTH_HEADER = "Authorization";
	private final String KEY = "member";

	@Override
	public ResultDTO execute(Model model, UserDAO dao) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		LoginDTO loginInfo = (LoginDTO) map.get("loginInfo");
		String token = null;
		UserInfoDTO dto = dao.searchMember(loginInfo); // 사용자가 존재하는지
		ResultDTO result = null;

		if (dto != null) {// 로그인 email, pwd가 일치한다면
			if (dto.getBlock() < 5) {//계정 로그인 실패횟수가 5회가 아니라면
				
				dao.initBlockCount(dto.getUserCode());//블락 횟수 초기화
				dto.setBlock(0);
				
				dto.setPwd(null);
				token = ConstantJwtService.getJwtService().create(KEY, dto, "user"); // 토큰 생성
				((HttpServletResponse) map.get("response")).setHeader(AUTH_HEADER, token);
				// 헤더에 토큰 포함
				result = ResultDTO.createInstance(true).setMsg("로그인 성공");
			}
			else {
				result = ResultDTO.createInstance(false).setMsg("블락된 계정입니다");
			}
		} else {// 로그인 email, pwd가 일치하지 않는 경우
			dao.incrementBlockCount(loginInfo.getEmail());
			result = ResultDTO.createInstance(false).setMsg("이메일이나 패스워드 불일치");
		}
		return result;
	}
}
