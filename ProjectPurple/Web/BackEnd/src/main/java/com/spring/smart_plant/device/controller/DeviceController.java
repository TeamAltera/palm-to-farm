package com.spring.smart_plant.device.controller;

import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.service.rabbitmq.Producer;
import com.spring.smart_plant.device.command.AddDeviceCommand;
import com.spring.smart_plant.device.command.DeleteDeviceCommand;
import com.spring.smart_plant.device.domain.DeviceInfoDTO;
import com.spring.smart_plant.device.domain.SFCodeDTO;

@RestController
@RequestMapping("/device")
public class DeviceController {
	
/*	private JdbcTemplate template = null;

	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		Constant.setTemplate(template);
	}*/
	
	@PostMapping(value = "/add")
	public ResultDTO addDevice(DeviceInfoDTO deviceInfo, Model model){
		model.addAttribute("deviceInfo", deviceInfo);
		return new AddDeviceCommand().execute(model);
	}
	
	@PostMapping(value = "/delete")
	public ResultDTO deleteDevice(SFCodeDTO sfCodeInfo, Model model){
		model.addAttribute("sfCodeInfo",sfCodeInfo);
		return new DeleteDeviceCommand().execute(model);
	}
	
	//테스트 나중에 지워야함
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public Map<String, Object> test(){
		Producer prod=new Producer();
		prod.send(null);
		return null;
	}
}
