package com.spring.smart_plant.device.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.validators.InnerIpDTOValidator;
import com.spring.smart_plant.device.command.AddDeviceAutoCommand;
import com.spring.smart_plant.device.command.AddDeviceCommand;
import com.spring.smart_plant.device.command.ConfirmAPCommand;
import com.spring.smart_plant.device.command.DeleteDeviceCommand;
import com.spring.smart_plant.device.command.getDeviceCommand;
import com.spring.smart_plant.device.domain.DeviceInfoDTO;
import com.spring.smart_plant.device.domain.IPDTO;

@RestController
@RequestMapping("/device")
public class DeviceController {
	
/*	//InnerIpDTO에 대한 유효성 검사
	@InitBinder("IpDTO")
	protected void initInnerIpDTOBinder(WebDataBinder binder) {
		binder.setValidator(new InnerIpDTOValidator());
	}*/
	
	//공유기, 수경재배기 수동 추가
	@PostMapping(value = "/add")
	public ResultDTO addDevice(@RequestBody  IPDTO pubIp, BindingResult result){
		//폼데이터의 유효성 검증결과
		InnerIpDTOValidator validator=new InnerIpDTOValidator();
		validator.validate(pubIp, result);
		if(result.hasErrors()) 
			return ResultDTO.createInstance(false).setMsg("입력 형식에 맞지 않습니다.");
		return new AddDeviceCommand().execute(pubIp.getInnerIp());
	}
	
	//공유기 등록되어진 상태에서, 수경재배기 개별 자동추가
	@PostMapping(value="/add/auto")
	public ResultDTO addDeviceAuto(@RequestBody DeviceInfoDTO deviceInfo) {
		return new AddDeviceAutoCommand().execute(deviceInfo);
	}
	
	//수경재배기 개별 수동 삭제
	@PostMapping(value = "/delete/{sfCode}")
	public ResultDTO deleteDevice(@PathVariable int sfCode){
		return new DeleteDeviceCommand().execute(sfCode);
	}
	
	//한개의 공유기, 해당 공유기에 등록된 수경재배기 모두 수동 삭제
	/*public ResultDTO deleteAPDevice() {
		
	}*/
	
	//수경 재배기 정보 조회
	@GetMapping(value= "/info")
	public ResultDTO getDeviceInfo() {
		return new getDeviceCommand().execute(null);
	}
	
	//공유기 사용 유무 조회
	@PostMapping(value="/confirm")
	public ResultDTO confirmAP(@RequestBody IPDTO innerIp, BindingResult result) {
		//폼데이터의 유효성 검증결과
		InnerIpDTOValidator validator=new InnerIpDTOValidator();
		validator.validate(innerIp, result);
		if(result.hasErrors()) 
			return ResultDTO.createInstance(false).setMsg("입력 형식에 맞지 않습니다.");
		return new ConfirmAPCommand().execute(innerIp.getInnerIp());
	}
	
	/*@PostMapping()
	public ResultDTO changeLEDMode(){
		
	}
	
	@PostMapping()
	public ResultDTO changeLEDMode(){
		
	}*/
	
	//테스트 나중에 지워야함
	/*@RequestMapping(value = "/test", method = RequestMethod.POST)
	public Map<String, Object> test(){
		Producer prod=new Producer();
		prod.send(null);
		return null;
	}*/
}
