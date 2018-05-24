package com.spring.smart_plant.user.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.user.dao.UserDAO;

@Service("signoutService")
public class SignoutServiceImpl implements IUserService{

	@Autowired
	private UserDAO dao;
	
	@Override
	public ResultDTO execute(Model model) {
		// TODO Auto-generated method stub
		/*HashMap<String, Object> hashMap=new HashMap<>();
		HttpSession session=((HttpServletRequest)model.asMap().get("request")).getSession(false);
		if(session!=null && session.getAttribute("userInfo")!=null) {
			System.out.println("session is invalidate");
			session.invalidate();
		}*/
		return ResultDTO.createInstance(true).setMsg("정상적으로 로그인 되었습니다.");
		/*hashMap.put("logoutResult", new LogoutResultDTO(true));
		return hashMap;*/
	}

}
