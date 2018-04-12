package com.spring.smart_plant.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.smart_plant.DTO.JSON.DeviceInfoJSON;
import com.spring.smart_plant.DTO.JSON.MemberIdJSON;
import com.spring.smart_plant.DTO.JSON.SfCodeJSON;
import com.spring.smart_plant.commands.AddDeviceCommand;
import com.spring.smart_plant.commands.AjaxCommand;
import com.spring.smart_plant.commands.DeleteDeviceCommand;
import com.spring.smart_plant.commands.MemberSearchCommand;
import com.spring.smart_plant.services.Producer;

@Controller
public class AjaxRequestController {
	
/*	private JdbcTemplate template = null;

	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		Constant.setTemplate(template);
	}*/
	
	//회원가입 페이지에서 아이디조회를 위해 사용, 많은 커넥션이 요구되어지므로 validation제외
	@RequestMapping(value = "/userExist", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> userExist(@RequestBody MemberIdJSON json, HttpServletRequest request){
		request.setAttribute("memberId", json.getMemberId());
		AjaxCommand command=new MemberSearchCommand();
		return command.execute(request);
	}
	
	@RequestMapping(value = "/addDevice", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addDevice(@RequestBody DeviceInfoJSON json, HttpServletRequest request){
		request.setAttribute("deviceInfo", json);
		AjaxCommand commad=new AddDeviceCommand();
		return commad.execute(request);
	}
	
	@RequestMapping(value = "/deleteDevice", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteDevice(@RequestBody SfCodeJSON json, HttpServletRequest request){
		request.setAttribute("sfCode", json.getSfCode());
		AjaxCommand commad=new DeleteDeviceCommand();
		return commad.execute(request);
	}
	
	//테스트 나중에 지워야함
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> test(){
		Producer prod=new Producer();
		prod.send(null);
		return null;
	}
}
