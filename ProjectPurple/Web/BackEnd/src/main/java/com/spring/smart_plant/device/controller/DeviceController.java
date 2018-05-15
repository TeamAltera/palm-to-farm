package com.spring.smart_plant.device.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.spring.smart_plant.device.command.AddAPCommand;
import com.spring.smart_plant.device.command.AddDeviceAutoCommand;
import com.spring.smart_plant.device.command.ConfirmAPCommand;
import com.spring.smart_plant.device.command.DeleteApCommand;
import com.spring.smart_plant.device.command.DeleteDeviceCommand;
import com.spring.smart_plant.device.command.DeviceControlCommand;
import com.spring.smart_plant.device.command.getDeviceCommand;
import com.spring.smart_plant.device.dao.DeviceDAO;
import com.spring.smart_plant.device.domain.CommandDTO;
import com.spring.smart_plant.device.domain.DeviceInfoDTO;
import com.spring.smart_plant.device.domain.IpDTO;

@RestController
@RequestMapping("/device")
public class DeviceController {
	
	@Autowired
	private DeviceDAO dao;
	
	//InnerIpDTO에 대한 유효성 검사, 첫글자는 반드시 소문자여야 
	@InitBinder("ipDTO")
	protected void initInnerIpDTOBinder(WebDataBinder binder) {
		binder.setValidator(new IpDTOValidator());
	}
	
	//CommandDTO에 대한 유효성 검사, 첫글자는 반드시 소문자여야
	@InitBinder("commandDTO")
	protected void initCommandDTOBinder(WebDataBinder binder) {
		binder.setValidator(new CommandDTOValidator());
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/device/add/ap/manual
	 * 공유기, 수경재배기 수동 추가, JWT토큰 필요
	 * {ip: string}
	 * -ip: 공유기 주소(public ip)
	 * </pre>
	 * @param pubIp
	 * @param result
	 * @return
	 */
	@PostMapping(value = "/add/ap/manual")
	public ResultDTO addAPAndDevice(@RequestBody @Valid IpDTO pubIp, BindingResult result){
		if(result.hasErrors()) 
			return ResultDTO.createInstance(false).setMsg("올바른 IP주소가 아닙니다.").setData(result.getAllErrors());
		return new AddAPCommand().execute(pubIp.getIp(), dao);
	}
	
	/**
	 * <pre>
	 * target: device
	 * http://localhost:9001/smart_plant/device/add/sf/auto
	 * 공유기 등록되어진 상태에서, 수경재배기 개별 자동추가
	 * </pre>
	 * @param deviceInfo
	 * @param result
	 * @return
	 */
	@PostMapping(value="/add/sf/auto")
	public ResultDTO addDeviceAuto(@RequestBody DeviceInfoDTO deviceInfo, BindingResult result) {
		System.out.println("add auto");
		IpDTOValidator validator=new IpDTOValidator();
		validator.validate(deviceInfo.getIpInfo(), result);
		if(result.hasErrors())
			return ResultDTO.createInstance(false).setMsg("올바른 IP주소가 아닙니다.").setData(result.getAllErrors());
		return new AddDeviceAutoCommand().execute(deviceInfo, dao);
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/device/delete/sf/manual/{sfCode}
	 * 수경재배기 개별 수동 삭제, JWT토큰 필요
	 * -sfCode: 수경재배기 코드, /device/info로 부터 얻어온 정보로 부터 추출해서 사용해야
	 * </pre>
	 * @param sfCode
	 * @return
	 */
	@PostMapping(value = "/delete/sf/manual/{sfCode}")
	public ResultDTO deleteDevice(@PathVariable int sfCode){
		return new DeleteDeviceCommand().execute(sfCode, dao);
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/device/delete/ap/manual
	 * 공유기 개별 수동 삭제, JWT토큰 필요
	 * {ip : string}
	 * -ip: 공유기 주소(public ip)
	 * </pre>
	 * @param pubIp
	 * @param result
	 * @return
	 */
	@PostMapping(value = "/delete/ap/manual")
	public ResultDTO deleteAPAndDevice(@RequestBody @Valid IpDTO pubIp, BindingResult result){
		if(result.hasErrors()) 
			return ResultDTO.createInstance(false).setMsg("올바른 IP주소가 아닙니다.").setData(result.getAllErrors());
		return new DeleteApCommand().execute(pubIp, dao);
	}
	
	/**
	 * <pre>
	 * target: device
	 * http://localhost:9001/smart_plant/device/change/ap
	 * 공유기의 ip주소를 변경
	 * </pre>
	 * @return
	 */
	@PostMapping(value="/change/ap")
	public ResultDTO changeDevice() {
		return null;
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/device/info
	 * 수경 재배기 정보 조회, JWT토큰 필요
	 * </pre>
	 * @return
	 */
	@GetMapping(value= "/info")
	public ResultDTO getDeviceInfo() {
		return new getDeviceCommand().execute(null, dao);
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/device/confirm
	 * 공유기 사용 유무 조회, JWT토큰 필요
	 * {ip: string}
	 * -ip: 공유기 주소(public ip)
	 * </pre>
	 * @param pubIp
	 * @param result
	 * @return
	 */
	@PostMapping(value="/confirm")
	public ResultDTO confirmAP(@RequestBody @Valid IpDTO pubIp, BindingResult result) {
		//폼데이터의 유효성 검증결과
		if(result.hasErrors()) 
			return ResultDTO.createInstance(false).setMsg("입력 형식에 맞지 않습니다.").setData(result.getAllErrors());
		return new ConfirmAPCommand().execute(pubIp.getIp(), dao);
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/device/control
	 * 기계 제어 명령, JWT토큰 필요
	 * {middle: string, dest: string, cmd: number}
	 * -middle: 공유기 ip주소(public ip)
	 * -dest: 수경재배기 ip주소(inner ip)
	 * -number: 제어코드
	 * </pre>
	 * @param command
	 * @param result
	 * @return
	 */
	@PostMapping(value="/control")
	public ResultDTO deviceControl(@RequestBody @Valid CommandDTO command, BindingResult result) {
		if(result.hasErrors()) 
			return ResultDTO.createInstance(false).setMsg("잘못된 명령 입니다.").setData(result.getAllErrors());
		return new DeviceControlCommand().execute(command, dao);
	}
	
	//테스트 나중에 지워야함
	/*@RequestMapping(value = "/test", method = RequestMethod.POST)
	public Map<String, Object> test(){
		Producer prod=new Producer();
		prod.send(null);
		return null;
	}*/
}
