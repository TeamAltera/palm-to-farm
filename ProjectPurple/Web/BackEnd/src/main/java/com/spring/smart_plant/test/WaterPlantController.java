package com.spring.smart_plant.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class WaterPlantController {

	// 로그인 페이지로 이동
	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}

/*	// 로그아웃시 세션 만료
	@RequestMapping("/logout")
	public String logout(Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		UserCommand command = new LogoutCommand();
		command.execute(model);
		return "redirect:login";
	}
*/
	// 패스워드 찾기 페이지로 이동
	@RequestMapping("/find_password")
	public String findPassword(Model model, HttpServletRequest request) {
		return "find_password";
	}

	// 메인페이지 로딩 시 DB로부터 각종정보 로드
	@RequestMapping("/main")
	public String main(Model model, HttpServletRequest request) {
		return "main";
	}

	// 로그인 시도시 수행하는 경로
/*	@RequestMapping(value = "/loginAction", method = RequestMethod.POST)
	public String login(Model model, HttpSession session,@Valid LoginDTO loginInfo,BindingResult result) {
		if(result.hasErrors()) {
			return "redirect:login";
		}
		model.addAttribute("loginInfo", loginInfo);
		model.addAttribute("session", session);
		UserCommand command = new LoginCommand();
		command.execute(model);
		return (String) model.asMap().get("forwardPage");
	}*/

	// 회원가입 경로매핑
	@RequestMapping(value = "/member_join", method = RequestMethod.GET)
	public String memberJoin(Model model) {
		return "member_join";
	}
/*
	// 회원가입 동작 수행
	@RequestMapping(value = "/memberJoinAction", method = RequestMethod.POST)
	public String memberJoinAction(UserInfoDTO userInfo, Model model) {
		model.addAttribute("userInfo", userInfo);
		UserCommand command = new MemberJoinCommand();
		command.execute(model);
		return "login";
	}*/

	//수경재배기 센싱정보 확인
	@RequestMapping(value = "/sensing_data")
	public String sensingInfo(Model model, HttpServletRequest request) {
		return "sensing_data";
	}
}
