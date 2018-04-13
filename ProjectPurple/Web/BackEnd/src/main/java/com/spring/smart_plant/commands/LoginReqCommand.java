package com.spring.smart_plant.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.rabbitmq.client.RpcClient.Response;
import com.spring.smart_plant.DAO.DAO;
import com.spring.smart_plant.DTO.LoginDTO;
import com.spring.smart_plant.DTO.SmartFarmInfoDTO;
import com.spring.smart_plant.DTO.UserInfoDTO;
import com.spring.smart_plant.DTO.Response.LoginResultDTO;
import com.spring.smart_plant.utills.ConstantJwtService;

public class LoginReqCommand implements UserRequestCommand{
	
	@Override
	public Map<String, Object> execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map=model.asMap();
		HashMap<String, Object> hashMap=new HashMap<String, Object>();
		if(((BindingResult)map.get("validationResult")).hasErrors()) { //폼 데이터 유효성 검증 실패 시 false반환 
			hashMap.put("loginResult", new LoginResultDTO(false,""));
		}
		LoginDTO loginInfo=(LoginDTO)map.get("loginInfo");
		HttpSession session=(HttpSession)map.get("session");
		String token=null;
		DAO dao=new DAO();
		UserInfoDTO dto=dao.searchMember(loginInfo);
		if(dto!=null) {//로그인 email, pwd가 일치한다면
			System.out.println("correct id/pwd");
			//if(session.getAttribute("userInfo")==null) { //세션에 사용자정보가 등록되있지 않다면 등록 
				System.out.println("Token is created");
				token=ConstantJwtService.getJwtService().create("member", dto, "user"); //토큰 생성
				((HttpServletResponse)map.get("response")).setHeader("Authorization", token);
			//}
			ArrayList<SmartFarmInfoDTO> machines=dao.getAllSmartPlant(dto.getUserCode());
			//사용자에게 등록된 모든 수경재배기 정보
			hashMap.put("loginResult", new LoginResultDTO(true,"",token,machines));
		}
		return hashMap;
	}
}
