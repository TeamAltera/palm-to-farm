package smart_farm_api.plant;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.device.domain.CommandDto;
import smart_farm_api.plant.domain.InfoDto;
import smart_farm_api.plant.service.FarmingServiceImpl;
import smart_farm_api.plant.service.GetGrowthPlantServiceImpl;
import smart_farm_api.plant.service.GetPortInfoServiceImpl;

@RestController
@Api(value = "식물생육 CRUD")
@RequestMapping("/plant")
@AllArgsConstructor
public class PlantController {
	
	private GetGrowthPlantServiceImpl getGrowthPlantService;
	
	private FarmingServiceImpl farmingService;
	
	private GetPortInfoServiceImpl getPortInfoService;
	
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
	public ResultDto getFarmingInfo(@RequestBody InfoDto info) throws Exception {
		return getGrowthPlantService.execute(info);
	}
	
	@ApiOperation(value = "식물 재배 상태변경, target:front")
	@PostMapping(value = "/farming")
	public ResultDto changeFarming(@RequestBody @Valid CommandDto command, BindingResult result) throws Exception {
		if (result.hasErrors())
			return ResultDto.createInstance(false).setMsg("잘못된 명령 입니다.").setData(result.getAllErrors());
		return farmingService.execute(command);
	}
	
	@ApiOperation(value = "포트 정보, target:front")
	@PostMapping(value = "/port")
	public ResultDto getPortInfo(@RequestBody InfoDto info) throws Exception {
		return getPortInfoService.execute(info);
	}
}
