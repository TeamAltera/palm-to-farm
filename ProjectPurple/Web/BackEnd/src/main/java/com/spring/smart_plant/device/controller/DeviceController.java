package com.spring.smart_plant.device.controller;

import javax.validation.Valid;

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
import com.spring.smart_plant.common.validators.CommandDTOValidator;
import com.spring.smart_plant.common.validators.IpDTOValidator;
import com.spring.smart_plant.device.command.AddDeviceAutoCommand;
import com.spring.smart_plant.device.command.AddAPCommand;
import com.spring.smart_plant.device.command.ConfirmAPCommand;
import com.spring.smart_plant.device.command.DeleteApCommand;
import com.spring.smart_plant.device.command.DeleteDeviceCommand;
import com.spring.smart_plant.device.command.DeviceControlCommand;
import com.spring.smart_plant.device.command.getDeviceCommand;
import com.spring.smart_plant.device.domain.CommandDTO;
import com.spring.smart_plant.device.domain.DeviceInfoDTO;
import com.spring.smart_plant.device.domain.IpDTO;

@RestController
@RequestMapping("/device")
public class DeviceController {
	
	//InnerIpDTO에 대한 유효성 검사, 첫글자는 반드시 소문자여야 
	@InitBinder("ipDTO")
	protected void initInnerIpDTOBinder(WebDataBinder binder) {
		binder.setValidator(new IpDTOValidator());
	}
	
	@InitBinder("commandDTO")
	protected void initCommandDTOBinder(WebDataBinder binder) {
		binder.setValidator(new CommandDTOValidator());
	}
	
	//공유기, 수경재배기 수동 추가, JWT토큰 필요
	@PostMapping(value = "/add/ap/manual")
	public ResultDTO addAPAndDevice(@RequestBody @Valid IpDTO pubIp, BindingResult result){
		if(result.hasErrors()) 
			return ResultDTO.createInstance(false).setMsg("올바른 IP주소가 아닙니다.").setData(result.getAllErrors());
		return new AddAPCommand().execute(pubIp.getInnerIp());
	}
	
	//공유기 등록되어진 상태에서, 수경재배기 개별 자동추가
	@PostMapping(value="/add/sf/auto")
	public ResultDTO addDeviceAuto(@RequestBody DeviceInfoDTO deviceInfo, BindingResult result) {
		System.out.println("add auto");
		IpDTOValidator validator=new IpDTOValidator();
		validator.validate(deviceInfo.getIpInfo(), result);
		if(result.hasErrors()) 
			return ResultDTO.createInstance(false).setMsg("올바른 IP주소가 아닙니다.").setData(result.getAllErrors());
		return new AddDeviceAutoCommand().execute(deviceInfo);
	}
	
	//수경재배기 개별 수동 삭제, JWT토큰 필요
	@PostMapping(value = "/delete/sf/manual/{sfCode}")
	public ResultDTO deleteDevice(@PathVariable int sfCode){
		return new DeleteDeviceCommand().execute(sfCode);
	}
	
	//공유기 개별 수동 삭제, JWT토큰 필요
	@PostMapping(value = "/delete/ap/manual")
	public ResultDTO deleteAPAndDevice(@RequestBody @Valid IpDTO innerIp, BindingResult result){
		if(result.hasErrors()) 
			return ResultDTO.createInstance(false).setMsg("올바른 IP주소가 아닙니다.").setData(result.getAllErrors());
		return new DeleteApCommand().execute(innerIp);
	}
	
	//공유기의 ip주소를 변경
	@PostMapping(value="/change/ap")
	public ResultDTO changeDevice() {
		return null;
	}
	
	//수경 재배기 정보 조회, JWT토큰 필요
	@GetMapping(value= "/info")
	public ResultDTO getDeviceInfo() {
		return new getDeviceCommand().execute(null);
	}
	
	//공유기 사용 유무 조회, JWT토큰 필요
	@PostMapping(value="/confirm")
	public ResultDTO confirmAP(@RequestBody @Valid IpDTO pubIp, BindingResult result) {
		//폼데이터의 유효성 검증결과
		if(result.hasErrors()) 
			return ResultDTO.createInstance(false).setMsg("입력 형식에 맞지 않습니다.").setData(result.getAllErrors());
		return new ConfirmAPCommand().execute(pubIp.getInnerIp());
	}

	//기계 제어 명령, JWT토큰 필요
	@PostMapping(value="/control")
	public ResultDTO deviceControl(@RequestBody @Valid CommandDTO command, BindingResult result) {
		if(result.hasErrors()) 
			return ResultDTO.createInstance(false).setMsg("잘못된 명령 입니다.").setData(result.getAllErrors());
		return new DeviceControlCommand().execute(command);
	}
	
	//테스트 나중에 지워야함
	/*@RequestMapping(value = "/test", method = RequestMethod.POST)
	public Map<String, Object> test(){
		Producer prod=new Producer();
		prod.send(null);
		return null;
	}*/
}
