package com.spring.smart_plant.user.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.service.jwt.JwtService;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.common.validators.LoginDTOValidator;
import com.spring.smart_plant.common.validators.UserInfoDTOValidator;
import com.spring.smart_plant.user.command.GetUserInfoCommand;
import com.spring.smart_plant.user.command.JoinCommand;
import com.spring.smart_plant.user.command.JoinSearchCommand;
import com.spring.smart_plant.user.command.SigninCommand;
import com.spring.smart_plant.user.command.SignoutCommand;
import com.spring.smart_plant.user.dao.UserDAO;
import com.spring.smart_plant.user.domain.LoginDTO;
import com.spring.smart_plant.user.domain.UserInfoDTO;

/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("/user")
public class UserController {
	private JwtService jwtService;
	
	@Autowired
	private UserDAO dao;
	
	@Autowired
	public void setJwtService(JwtService jwtService) {
		this.jwtService = jwtService;
		ConstantJwtService.setJwtService(jwtService);
	}
	
	// initBinder에 검증하고자하는 타입을 명시해줘야, 그러지 안으면 전체 검사 첫 문자는 소문자
	@InitBinder("loginDTO")
	protected void initLoginDTOBinder(WebDataBinder binder) {
		binder.setValidator(new LoginDTOValidator());
	}
	
	@InitBinder("userInfoDTO")
	protected void initUserInfoDTOBinder(WebDataBinder binder) {
		binder.setValidator(new UserInfoDTOValidator());
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/user/signout
	 * 로그아웃시 토큰 만료
	 * </pre>
	 * @param model
	 * @return
	 */
	@PostMapping("/signout")
	public ResultDTO signout(Model model) {
		/*model.addAttribute("request", request);*/
		return new SignoutCommand().execute(model,dao);
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/user/signin
	 * {email: string, pwd: string}
	 * -email: 이메일
	 * -pwd: 패스워드
	 * 로그인 시도시 유효한 사용자라면 JWT토큰 발급
	 * </pre>
	 * @param model
	 * @param loginInfo
	 * @param result
	 * @param response
	 * @return
	 */
	@PostMapping(value="/signin")
	public ResultDTO login(Model model, @Valid @RequestBody LoginDTO loginInfo,
			BindingResult result, HttpServletResponse response) {
		if(result.hasErrors()) //폼데이터의 유효성 검증결과에 따른 ResultDTO생성
			return ResultDTO.createInstance(false).setMsg("입력 형식에 맞지 않습니다.").setData(result.getAllErrors());
		model.addAttribute("loginInfo", loginInfo);
		model.addAttribute("response", response);
		return new SigninCommand().execute(model,dao);
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/user/signup
	 * {pwd: string, email: string, firstName: string, secondName: string}
	 * -pwd: 패스워드
	 * -email: 가입하고자 하는 이메일
	 * -firstName: 성
	 * -secondName: 이름
	 * 회원가입 동작 수행 
	 * </pre>
	 * @param userInfo
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/signup")
	public ResultDTO memberJoinAction(@Valid @RequestBody UserInfoDTO userInfo, BindingResult result, Model model) {
		if(result.hasErrors()) { //폼데이터의 유효성 검증결과에 따른 ResultDTO생성
			return ResultDTO.createInstance(false).setMsg("입력 형식에 맞지 않습니다.").setData(result.getAllErrors());
		}
		model.addAttribute("userInfo", userInfo);
		return new JoinCommand().execute(model,dao);
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/user/info
	 * JWT토큰으로 부터 사용자 정보 반환
	 * </pre>
	 * @return
	 */
	@GetMapping(value="/info")
	public ResultDTO getUserInfo() {
		return new GetUserInfoCommand().execute(null,dao);
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/user/find/{email}
	 * 회원가입 페이지에서 아이디조회를 위해 사용, 많은 커넥션이 요구되어지므로 validation제외
	 * -email: 이메일(string type)
	 * </pre>
	 * @param email
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/find/{email}")
	public ResultDTO userExist(@PathVariable String email, Model model){
		model.addAttribute("emailInfo", email);
		return new JoinSearchCommand().execute(model,dao);
	}
}
