package com.spring.smart_plant.plant.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.device.domain.CommandDTO;
import com.spring.smart_plant.plant.command.DeleteGrowthPlantServiceImpl;
import com.spring.smart_plant.plant.command.FarmingServiceImpl;
import com.spring.smart_plant.plant.command.GetGrowthPlantServiceImpl;
import com.spring.smart_plant.plant.domain.InfoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "식물생육 CRUD")
@RequestMapping("/plant")
public class PlantController {
	
	@Autowired
	private GetGrowthPlantServiceImpl getGrowthPlantService;
	
	@Autowired
	private FarmingServiceImpl farmingService;
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/plant/farming
	 * 공유기, 수경재배기 수동 추가, JWT토큰 필요
	 * {sfCode: integer, apCode: integer, plantCode: integer}
	 * </pre>
	 * 
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "식물 정보, target:front")
	@PostMapping(value = "/info")
	public ResultDTO getFarmingInfo(@RequestBody InfoDTO info) throws Exception {
		return getGrowthPlantService.execute(info);
	}
	
	@ApiOperation(value = "식물 재배 상태변경, target:front")
	@PostMapping(value = "/farming")
	public ResultDTO changeFarming(@RequestBody @Valid CommandDTO command, BindingResult result) throws Exception {
		if (result.hasErrors())
			return ResultDTO.createInstance(false).setMsg("잘못된 명령 입니다.").setData(result.getAllErrors());
		return farmingService.execute(command);
	}
}
