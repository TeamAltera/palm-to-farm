package smart_farm_api.sensor;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import smart_farm_api.common.DateSearchDto;
import smart_farm_api.common.ResultDto;
import smart_farm_api.common.validators.DateSearchDtoValidator;
import smart_farm_api.sensor.service.GetSensorDataServiceImpl;
import smart_farm_api.sensor.service.GetSensorLastDataService;

/**
 * @author AN
 *
 */
@RestController
@RequestMapping("/sensor")
@AllArgsConstructor
public class SensorDataController {

	@InitBinder("dateSearchDto")
	protected void initUserInfoDTOBinder(WebDataBinder binder) {
		binder.setValidator(new DateSearchDtoValidator());
	}

	private GetSensorDataServiceImpl getSensorDataService;
	
	private GetSensorLastDataService getSensorLastDataService;
	/**
	 *<pre>
	 * target: front
	 * http://localhost:9001/smart_plant/sensor/day
	 * {date: string, sfCode: int, apCode: int}
	 * 날짜, 수경재배기번호에 따른 로그기록 조회
	 * -date: "yyyy-MM-dd" 포맷에 따른 문자열
	 * -sfCode: 수경재배기 코드, device/info를 통해 얻어온 값을 가지고사용해야함.
	 * -apCode: 공유기 코드, device/info를 통해 얻어온 값을 가지고사용해야함.
	 * </pre>
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "센싱 데이터 날짜별 조회, target:front")
	@PostMapping("/day")
	public ResultDto getAllDataSet(@Valid @RequestBody DateSearchDto dto, BindingResult result) {
		// validation 수행
		if (result.hasErrors()) {
			return ResultDto.createInstance(false).setMsg("입력 형식에 맞지 않습니다.").setData(result.getAllErrors());
		}
		return getSensorDataService.execute(dto);
	}
	
	@PostMapping("/day/last")
	public ResultDto getLastDataSet(@Valid @RequestBody DateSearchDto dto, BindingResult result) {
		// validation 수행
		if (result.hasErrors()) {
			return ResultDto.createInstance(false).setMsg("입력 형식에 맞지 않습니다.").setData(result.getAllErrors());
		}
		return getSensorLastDataService.execute(dto);
	}
}
