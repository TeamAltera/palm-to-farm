package com.spring.smart_plant.user.command;

import org.springframework.ui.Model;

import com.spring.smart_plant.common.domain.ResultDTO;

public class SignoutCommand implements IUserCommand{

	@Override
	public ResultDTO execute(Model model) {
		// TODO Auto-generated method stub
		/*HashMap<String, Object> hashMap=new HashMap<>();
		HttpSession session=((HttpServletRequest)model.asMap().get("request")).getSession(false);
		if(session!=null && session.getAttribute("userInfo")!=null) {
			System.out.println("session is invalidate");
			session.invalidate();
		}*/
		return ResultDTO.createInstance(true);
		/*hashMap.put("logoutResult", new LogoutResultDTO(true));
		return hashMap;*/
	}

}
