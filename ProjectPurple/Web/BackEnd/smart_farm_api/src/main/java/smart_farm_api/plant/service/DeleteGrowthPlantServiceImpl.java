package smart_farm_api.plant.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.plant.domain.InfoDto;
import smart_farm_api.plant.repository.PlantMapper;

@Service
@AllArgsConstructor
public class DeleteGrowthPlantServiceImpl implements IPlantService{
	
	private PlantMapper plantMapper;
	
	@Override
	public ResultDto execute(Object obj) throws Exception {
		// TODO Auto-generated method stub
		InfoDto info=(InfoDto)obj;
		plantMapper.deleteGrowthPlant(info.getApCode(), info.getStamp());
		return null;
	}

}
