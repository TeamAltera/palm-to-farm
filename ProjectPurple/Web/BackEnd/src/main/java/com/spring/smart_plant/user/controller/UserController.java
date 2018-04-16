package com.spring.smart_plant.user.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.service.jwt.JwtService;
import com.spring.smart_plant.common.utills.ConstantJDBCTemplate;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.common.validators.LoginDTOValidator;
import com.spring.smart_plant.common.validators.UserInfoDTOValidator;
import com.spring.smart_plant.user.command.JoinCommand;
import com.spring.smart_plant.user.command.JoinSearchCommand;
import com.spring.smart_plant.user.command.LoginCommand;
import com.spring.smart_plant.user.command.LogoutCommand;
import com.spring.smart_plant.user.domain.EmailDTO;
import com.spring.smart_plant.user.domain.LoginDTO;
import com.spring.smart_plant.user.domain.UserInfoDTO;

/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private JdbcTemplate template = null;
	private JwtService jwtService;

	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		ConstantJDBCTemplate.setTemplate(template);
	}
	
	@Autowired
	public void setJwtService(JwtService jwtService) {
		this.jwtService = jwtService;
		ConstantJwtService.setJwtService(jwtService);
	}
	
	// initBinder에 검증하고자하는 타입을 명시해줘야, 그러지 안으면 전체 검사
	@InitBinder("loginDTO")
	protected void initLoginDTOBinder(WebDataBinder binder) {
		binder.setValidator(new LoginDTOValidator());
	}
	
	@InitBinder("UserInfoDTO")
	protected void initUserInfoDTOBinder(WebDataBinder binder) {
		binder.setValidator(new UserInfoDTOValidator());
	}

	// 로그인 페이지로 이동
	/*@RequestMapping("/login")
	public String login(Model model) {
		return "redirect:http://203.250.32.39:3000/";
		 return "login"; 
	}*/

	// 로그아웃시 토큰 만료
	@PostMapping("/logout")
	public ResultDTO logout(Model model) {
		/*model.addAttribute("request", request);*/
		return new LogoutCommand().execute(model);
	}

	// 로그인 시도시 수행하는 경로
	@PostMapping(value="/signin")
	public ResultDTO login(Model model, @Valid @RequestBody LoginDTO loginInfo,
			BindingResult result, HttpServletResponse response) {
		if(result.hasErrors()) //폼데이터의 유효성 검증결과에 따른 ResultDTO생성
			return ResultDTO.createInstance(false).setMsg("입력 형식에 맞지 않습니다.");
		model.addAttribute("loginInfo", loginInfo);
		model.addAttribute("response", response);
		return new LoginCommand().execute(model);
	}

	// 회원가입 동작 수행
	@PostMapping(value = "/signup")
	public ResultDTO memberJoinAction(@Valid @RequestBody UserInfoDTO userInfo, Model model) {
		model.addAttribute("userInfo", userInfo);
		return new JoinCommand().execute(model);
	}
	
	//회원가입 페이지에서 아이디조회를 위해 사용, 많은 커넥션이 요구되어지므로 validation제외
	@PostMapping(value = "/finduser")
	public ResultDTO userExist(EmailDTO emailInfo, Model model){
		model.addAttribute("emailInfo", emailInfo);
		return new JoinSearchCommand().execute(model);
	}
}
