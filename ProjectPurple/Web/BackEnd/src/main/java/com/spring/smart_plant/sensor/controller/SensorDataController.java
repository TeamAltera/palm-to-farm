package com.spring.smart_plant.sensor.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.smart_plant.common.domain.DateSearchDTO;
import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.validators.DateSearchDTOValidator;
import com.spring.smart_plant.sensor.command.GetSensorDataServiceImpl;

import io.swagger.annotations.ApiOperation;

/**
 * @author AN
 *
 */
@RestController
@RequestMapping("/sensor")
public class SensorDataController {

	@InitBinder("dateSearchDTO")
	protected void initUserInfoDTOBinder(WebDataBinder binder) {
		binder.setValidator(new DateSearchDTOValidator());
	}
	
	@Autowired
	private GetSensorDataServiceImpl getSensorDataService; 

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
	public ResultDTO getAllDataSet(@Valid @RequestBody DateSearchDTO dto, BindingResult result) {
		// validation 수행
		if (result.hasErrors()) {
			return ResultDTO.createInstance(false).setMsg("입력 형식에 맞지 않습니다.").setData(result.getAllErrors());
		}
		return getSensorDataService.execute(dto);
	}
}
