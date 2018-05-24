package com.spring.smart_plant.sensor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.smart_plant.common.domain.ResultDTO;

@RestController
@RequestMapping("/sensor")
public class SensorDataController {
	
	@GetMapping("/get/last")
	public ResultDTO getLatestDataSet() {
		return ResultDTO.createInstance(true);
	}
	
	@GetMapping("/get/day")
	public ResultDTO getAllDataSet() {
		return ResultDTO.createInstance(true);
	}
}
