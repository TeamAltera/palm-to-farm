package com.spring.smart_plant.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.log.command.GetLogServiceImpl;
import com.spring.smart_plant.log.domain.DeviceLogSearchDTO;

@RestController
@RequestMapping("/log")
public class DeviceLogController {
	
	@Autowired
	private GetLogServiceImpl getLogService;
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/log/day
	 * {dateString: string, sfCode: int}
	 * 날짜, 수경재배기번호에 따른 로그기록 조회
	 * -dateString: yyyy//MM/dd 포맷에 따른 문자열
	 * -sfCode: 수경재배기 코드, device/info를 통해 얻어온 값을 가지고사용해야함.
	 * </pre>
	 * @param dto
	 * @return
	 */
	@PostMapping("/day")
	public ResultDTO getAllLog(@RequestBody DeviceLogSearchDTO dto) {
		System.out.println(dto.getDate());
		return getLogService.execute(dto);
	} 
}
