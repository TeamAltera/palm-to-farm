package smart_farm_api.user.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;

@Service
@AllArgsConstructor
public class SignoutServiceImpl implements IUserService{

	//private UserMapper userMapper;
	
	@Override
	public ResultDto execute(Model model) {
		// TODO Auto-generated method stub
		/*HashMap<String, Object> hashMap=new HashMap<>();
		HttpSession session=((HttpServletRequest)model.asMap().get("request")).getSession(false);
		if(session!=null && session.getAttribute("userInfo")!=null) {
			System.out.println("session is invalidate");
			session.invalidate();
		}*/
		return ResultDto.createInstance(true).setMsg("정상적으로 로그아웃 되었습니다.");
		/*hashMap.put("logoutResult", new LogoutResultDTO(true));
		return hashMap;*/
	}

}
