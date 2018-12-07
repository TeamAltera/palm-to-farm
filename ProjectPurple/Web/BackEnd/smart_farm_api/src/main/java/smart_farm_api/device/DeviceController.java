package smart_farm_api.device;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.common.validators.CommandDtoValidator;
import smart_farm_api.common.validators.IpDtoValidator;
import smart_farm_api.device.domain.ChangeSfDto;
import smart_farm_api.device.domain.CommandDto;
import smart_farm_api.device.domain.DeviceInfoDto;
import smart_farm_api.device.domain.IpDto;
import smart_farm_api.device.service.AddAPServiceImpl;
import smart_farm_api.device.service.AddDeviceAutoServiceImpl;
import smart_farm_api.device.service.ChangeDeviceServiceImpl;
import smart_farm_api.device.service.ConfirmAPServiceImpl;
import smart_farm_api.device.service.DeleteApServiceImpl;
import smart_farm_api.device.service.DeleteDeviceServiceImpl;
import smart_farm_api.device.service.DeviceControlServiceImpl;
import smart_farm_api.device.service.GetDeviceServiceImpl;

@RestController
@Api(value = "장치 CRUD")
@RequestMapping("/device")
@AllArgsConstructor
public class DeviceController {

	private DeviceControlServiceImpl deviceControlService;

	private AddAPServiceImpl addAPService;

	private AddDeviceAutoServiceImpl addDeviceAutoService;

	private ConfirmAPServiceImpl confirmAPService;

	private DeleteApServiceImpl deleteApService;

	private DeleteDeviceServiceImpl deleteDeviceService;

	private GetDeviceServiceImpl getDeviceService;
	
	private ChangeDeviceServiceImpl changeDeviceService;

	// InnerIpDTO에 대한 유효성 검사, 첫글자는 반드시 소문자여야
	@InitBinder("ipDto")
	protected void initInnerIpDTOBinder(WebDataBinder binder) {
		binder.setValidator(new IpDtoValidator());
	}

	// CommandDTO에 대한 유효성 검사, 첫글자는 반드시 소문자여야
	@InitBinder("commandDto")
	protected void initCommandDTOBinder(WebDataBinder binder) {
		binder.setValidator(new CommandDtoValidator());
	}

	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/device/add/ap/manual
	 * 공유기, 수경재배기 수동 추가, JWT토큰 필요
	 * {ip: string}
	 * -ip: 공유기 주소(public ip)
	 * </pre>
	 * 
	 * @param pubIp
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "공유기 수동 추가, target:front")
	@PostMapping(value = "/add/ap/manual")
	public ResultDto addAPAndDevice(@RequestBody @Valid IpDto pubIp, BindingResult result) {
		if (result.hasErrors())
			return ResultDto.createInstance(false).setMsg("올바른 IP주소가 아닙니다.").setData(result.getAllErrors());
		try {
			return addAPService.execute(pubIp.getIp());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultDto.createInstance(false).setMsg("등록 오류입니다.");
		}
	}

	/**
	 * <pre>
	 * target: device
	 * http://localhost:9001/smart_plant/device/add/sf/auto
	 * 공유기 등록되어진 상태에서, 수경재배기 개별 자동추가
	 * {apCode: 공유기코드, ipInfo: 수경재배기ip}
	 * </pre>
	 * 
	 * @param deviceInfo
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "수경재배기 자동 추가, target:device")
	@PostMapping(value = "/add/sf/auto")
	public ResultDto addDeviceAuto(@RequestBody DeviceInfoDto deviceInfo) {
		return addDeviceAutoService.execute(deviceInfo);
	}

	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/device/delete/sf/manual/{sfCode}
	 * 수경재배기 개별 수동 삭제, JWT토큰 필요
	 * -sfCode: 수경재배기 코드, /device/info로 부터 얻어온 정보로 부터 추출해서 사용해야
	 * </pre>
	 * 
	 * @param sfCode
	 * @return
	 */
	@ApiOperation(value = "수경재배기 수동 삭제, target:front")
	@PostMapping(value = "/delete/sf/manual/{stamp}")
	public ResultDto deleteDevice(@PathVariable String stamp) {
		return deleteDeviceService.execute(Integer.parseInt(stamp));
	}

	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/device/delete/ap/manual/{apCode}
	 * 공유기 개별 수동 삭제, JWT토큰 필요
	 * apCode: 공유기 코드
	 * </pre>
	 * 
	 * @param pubIp
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "공유기 수동 삭제, target:front")
	@PostMapping(value = "/delete/ap/manual/{apCode}")
	public ResultDto deleteAPAndDevice(@PathVariable int apCode) {
		try {
			return deleteApService.execute(apCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResultDto.createInstance(false).setMsg("삭제가 실패하였습니다.");
		}
	}

	/**
	 * <pre>
	 * target: device
	 * http://localhost:9001/smart_plant/device/change/ap
	 * 공유기의 ip주소를 변경
	 * </pre>
	 * 
	 * @return
	 */
	@ApiOperation(value = "디바이스 IP주소 변경, target:device")
	@PostMapping(value = "/change")
	public ResultDto changeDevice(@RequestBody ChangeSfDto changeSfDto) {
		System.out.println("ch");
		return changeDeviceService.execute(changeSfDto);
	}

	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/device/info
	 * 수경 재배기 정보 조회, JWT토큰 필요
	 * </pre>
	 * 
	 * @return
	 */
	@ApiOperation(value = "공유기, 수경재배기 전체 정보 조회, target:front")
	@GetMapping(value = "/info")
	public ResultDto getDeviceInfo() {
		return getDeviceService.execute(null);
	}

	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/device/confirm
	 * 공유기 사용 유무 조회, JWT토큰 필요
	 * {ip: string}
	 * -ip: 공유기 주소(public ip)
	 * </pre>
	 * 
	 * @param pubIp
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "존재하는 공유기인지 조회, target:front")
	@PostMapping(value = "/confirm")
	public ResultDto confirmAP(@RequestBody @Valid IpDto pubIp, BindingResult result) {
		// 폼데이터의 유효성 검증결과
		if (result.hasErrors())
			return ResultDto.createInstance(false).setMsg("입력 형식에 맞지 않습니다.").setData(result.getAllErrors());
		return confirmAPService.execute(pubIp.getIp());
	}

	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/device/control
	 * 기계 제어 명령, JWT토큰 필요
	 * {middle: string, dest: string, cmd: number, sfCode: number, apCode: number, usedIp: string}
	 * -middle: 공유기 ip주소(public ip)
	 * -dest: 수경재배기 ip주소(inner ip)
	 * -cmd: 제어코드
	 * -sfCode: 수경재배기 코드 device/info에서 받은 정보에서 추출해서 사용해야
	 * -apCode: 수경재배기 공유기 코드 device/info에서 받은 정보에서 추출해서 사용해야
	 * -usedIp: 명령을 수행한 클라이언트 ip
	 * 2: LED자동
	 * 3: LED수동
	 * 4: LED켜기
	 * 5: LED끄기
	 * </pre>
	 * 
	 * @param command
	 * @param result
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "재배기 제어, target:front")
	@PostMapping(value = "/control")
	public ResultDto deviceControl(@RequestBody @Valid CommandDto command, BindingResult result) throws Exception {
		if (result.hasErrors())
			return ResultDto.createInstance(false).setMsg("잘못된 명령 입니다.").setData(result.getAllErrors());
		return deviceControlService.execute(command);
	}

	// 테스트 나중에 지워야함
	/*
	 * @RequestMapping(value = "/test", method = RequestMethod.POST) public
	 * Map<String, Object> test(){ Producer prod=new Producer(); prod.send(null);
	 * return null; }
	 */
}
