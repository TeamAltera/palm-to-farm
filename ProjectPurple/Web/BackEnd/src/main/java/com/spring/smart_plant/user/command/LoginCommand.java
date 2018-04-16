package com.spring.smart_plant.user.command;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.spring.smart_plant.DAO.DAO;
import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.user.domain.LoginDTO;
import com.spring.smart_plant.user.domain.UserInfoDTO;

public class LoginCommand implements UserCommand {

	@Override
	public ResultDTO execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		LoginDTO loginInfo = (LoginDTO) map.get("loginInfo");
		String token = null;
		DAO dao = new DAO();
		UserInfoDTO dto = dao.searchMember(loginInfo); //사용자가 존재하는지
		ResultDTO result=null;
		
		if (dto != null) {// 로그인 email, pwd가 일치한다면
			System.out.println("Token is created");
			token = ConstantJwtService.getJwtService().create("member", dto, "user"); // 토큰 생성
			((HttpServletResponse) map.get("response")).setHeader("Authorization", token);
			//ArrayList<SmartFarmInfoDTO> machines = dao.getAllSmartPlant(dto.getUserCode());
			// 사용자에게 등록된 모든 수경재배기 정보
			/*hashMap.put("loginResult", new LoginResultDTO(true, "", token, machines));*/
			result=ResultDTO.createInstance(true).setData(dto);
		}
		else {//로그인 email, pwd가 일치하지 않는 경우
			result=ResultDTO.createInstance(false).setMsg("이메일이나 패스워드 불일치");
		}
		return result;
	}
}
