package com.spring.smart_plant.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.smart_plant.DTO.LoginDTO;
import com.spring.smart_plant.DTO.UserInfoDTO;
import com.spring.smart_plant.commands.LoginCommand;
import com.spring.smart_plant.commands.LogoutCommand;
import com.spring.smart_plant.commands.MainCommand;
import com.spring.smart_plant.commands.MemberJoinCommand;
import com.spring.smart_plant.commands.SensingDataCommand;
import com.spring.smart_plant.commands.UserCommand;
import com.spring.smart_plant.services.Producer;
import com.spring.smart_plant.utills.ConstantJDBCTemplate;
import com.spring.smart_plant.validators.LoginDTOValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class WaterPlantController {

	private JdbcTemplate template = null;

	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		ConstantJDBCTemplate.setTemplate(template);
	}
	
	//initBinder에 검증하고자하는 타입을 명시해줘야, 그러지 안으면 전체 검사
	@InitBinder("loginDTO")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new LoginDTOValidator());
	}

	// 로그인 페이지로 이동
	@RequestMapping("/login")
	public String login(Model model) {
		return "forward:http://localhost:9002";
		/*return "login";*/
	}

	// 로그아웃시 세션 만료
	@RequestMapping("/logout")
	public String logout(Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		UserCommand command = new LogoutCommand();
		command.execute(model);
		return "redirect:login";
	}

	// 패스워드 찾기 페이지로 이동
	@RequestMapping("/find_password")
	public String findPassword(Model model, HttpServletRequest request) {
		return "find_password";
	}

	// 메인페이지 로딩 시 DB로부터 각종정보 로드
	@RequestMapping("/main")
	public String main(Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		UserCommand command = new MainCommand();
		command.execute(model);
		return "main";
	}

	// 로그인 시도시 수행하는 경로
	@RequestMapping(value = "/loginAction", method = RequestMethod.POST)
	public String login(Model model, HttpSession session,@Valid LoginDTO loginInfo,BindingResult result) {
		if(result.hasErrors()) {
			return "redirect:login";
		}
		model.addAttribute("loginInfo", loginInfo);
		model.addAttribute("session", session);
		UserCommand command = new LoginCommand();
		command.execute(model);
		return (String) model.asMap().get("forwardPage");
	}

	// 회원가입 경로매핑
	@RequestMapping(value = "/member_join", method = RequestMethod.GET)
	public String memberJoin(UserInfoDTO userInfo, Model model) {
		return "member_join";
	}

	// 회원가입 동작 수행
	@RequestMapping(value = "/memberJoinAction", method = RequestMethod.POST)
	public String memberJoinAction(UserInfoDTO userInfo, Model model) {
		model.addAttribute("userInfo", userInfo);
		UserCommand command = new MemberJoinCommand();
		command.execute(model);
		return "login";
	}

	//수경재배기 센싱정보 확인
	@RequestMapping(value = "/sensing_data")
	public String sensingInfo(Model model, HttpServletRequest request) {
		model.addAttribute("request",request);
		UserCommand command = new SensingDataCommand();
		command.execute(model);
		return "sensing_data";
	}
}
