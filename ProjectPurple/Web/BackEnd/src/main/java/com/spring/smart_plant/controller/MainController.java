package com.spring.smart_plant.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.smart_plant.DTO.LoginDTO;
import com.spring.smart_plant.DTO.UserInfoDTO;
import com.spring.smart_plant.commands.LoginReqCommand;
import com.spring.smart_plant.commands.LogoutReqCommand;
import com.spring.smart_plant.commands.MemberJoinReqCommand;
import com.spring.smart_plant.commands.SensingDataCommand;
import com.spring.smart_plant.commands.UserRequestCommand;
import com.spring.smart_plant.services.jwt.JwtService;
import com.spring.smart_plant.utills.ConstantJDBCTemplate;
import com.spring.smart_plant.utills.ConstantJwtService;
import com.spring.smart_plant.validators.LoginDTOValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {

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
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new LoginDTOValidator());
	}

	// 로그인 페이지로 이동
	@RequestMapping("/login")
	public String login(Model model) {
		return "redirect:http://203.250.32.39:3000/";
		/* return "login"; */
	}

	// 로그아웃시 세션 만료
	@RequestMapping("/logout")
	@ResponseBody
	public Map<String, Object> logout(Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		UserRequestCommand command = new LogoutReqCommand();
		return command.execute(model);
	}

	// 패스워드 찾기 페이지로 이동
	/*@RequestMapping("/find_password")
	public String findPassword(Model model, HttpServletRequest request) {
		return "find_password";
	}*/

	// 메인페이지 로딩 시 DB로부터 각종정보 로드
	/*@RequestMapping("/main")
	public String main(Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		UserCommand command = new MainCommand();
		command.execute(model);
		return "main";
	}*/

	// 로그인 시도시 수행하는 경로
	@RequestMapping(value = "/loginAction", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(Model model, HttpSession session, @Valid @RequestBody LoginDTO loginInfo,
			BindingResult result, HttpServletResponse response) {
		model.addAttribute("validationResult", result);
		model.addAttribute("loginInfo", loginInfo);
		model.addAttribute("response", response);
		model.addAttribute("session", session);
		UserRequestCommand command = new LoginReqCommand();
		return command.execute(model);
	}

	// 회원가입 동작 수행
	@RequestMapping(value = "/memberJoinAction", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> memberJoinAction(@RequestBody  UserInfoDTO userInfo, Model model) {
		model.addAttribute("userInfo", userInfo);
		UserRequestCommand command = new MemberJoinReqCommand();
		return command.execute(model);
	}

	// 수경재배기 센싱정보 확인
	/*@RequestMapping(value = "/sensing_data")
	public String sensingInfo(Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		UserRequestCommand command = new SensingDataCommand();
		command.execute(model);
		return "sensing_data";
	}*/
}
