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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.service.jwt.JwtService;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.common.validators.LoginDTOValidator;
import com.spring.smart_plant.common.validators.UserInfoDTOValidator;
import com.spring.smart_plant.user.command.GetUserInfoServiceImpl;
import com.spring.smart_plant.user.command.JoinSearchServiceImpl;
import com.spring.smart_plant.user.command.JoinServiceImpl;
import com.spring.smart_plant.user.command.SigninServiceImpl;
import com.spring.smart_plant.user.command.SignoutServiceImpl;
import com.spring.smart_plant.user.domain.LoginDTO;
import com.spring.smart_plant.user.domain.UserInfoDTO;

import io.swagger.annotations.ApiOperation;

/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("/user")
public class UserController {
	private JwtService jwtService;
	
	@Autowired
	public void setJwtService(JwtService jwtService) {
		this.jwtService = jwtService;
		ConstantJwtService.setJwtService(jwtService);
	}
	
	@Autowired
	private SignoutServiceImpl signoutService;
	
	@Autowired
	private SigninServiceImpl signinService;
	
	@Autowired
	private JoinServiceImpl joinService;
	
	@Autowired
	private JoinSearchServiceImpl joinSearchService;
	
	@Autowired
	private GetUserInfoServiceImpl getUserInfoService;
	
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
	@ApiOperation(value = "로그아웃, target:front")
	@PostMapping("/signout")
	public ResultDTO signout(Model model) {
		/*model.addAttribute("request", request);*/
		return signoutService.execute(model);
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
	@ApiOperation(value = "로그인, target:front")
	@PostMapping(value="/signin")
	public ResultDTO login(Model model, @Valid @RequestBody LoginDTO loginInfo,
			BindingResult result, HttpServletResponse response) {
		if(result.hasErrors()) //폼데이터의 유효성 검증결과에 따른 ResultDTO생성
			return ResultDTO.createInstance(false).setMsg("입력 형식에 맞지 않습니다.").setData(result.getAllErrors());
		model.addAttribute("loginInfo", loginInfo);
		model.addAttribute("response", response);
		return signinService.execute(model);
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
	@ApiOperation(value = "회원 가입, target:front")
	@PostMapping(value = "/signup")
	public ResultDTO memberJoinAction(@Valid @RequestBody UserInfoDTO userInfo, BindingResult result, Model model) {
		if(result.hasErrors()) { //폼데이터의 유효성 검증결과에 따른 ResultDTO생성
			return ResultDTO.createInstance(false).setMsg("입력 형식에 맞지 않습니다.").setData(result.getAllErrors());
		}
		model.addAttribute("userInfo", userInfo);
		return joinService.execute(model);
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/user/info
	 * JWT토큰으로 부터 사용자 정보 반환
	 * </pre>
	 * @return
	 */
	@ApiOperation(value = "사용자 정보 조회, target:front")
	@GetMapping(value="/info")
	public ResultDTO getUserInfo() {
		return getUserInfoService.execute(null);
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
	@ApiOperation(value = "등록된 이메일 인지 조회, target:front")
	@GetMapping(value = "/find")
	public ResultDTO userExist(@RequestParam("email") String email, Model model){
		model.addAttribute("emailInfo", email);
		return joinSearchService.execute(model);
	}
}
