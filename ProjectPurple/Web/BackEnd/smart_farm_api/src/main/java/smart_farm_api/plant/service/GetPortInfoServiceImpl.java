package smart_farm_api.plant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.plant.domain.InfoDto;
import smart_farm_api.plant.domain.PortStatusDto;
import smart_farm_api.plant.repository.PlantMapper;

@Service
@AllArgsConstructor
public class GetPortInfoServiceImpl implements IPlantService{

	private PlantMapper plantMapper;
	
	@Override
	public ResultDto execute(Object obj) throws Exception {
		// TODO Auto-generated method stub
		List<List<PortStatusDto>> out=new ArrayList<>();
		for(int i=0; i<4; i++) {
			List<PortStatusDto> list=new ArrayList<>();
			for (int j = 0; j < 8; j++) {
				list.add(new PortStatusDto(false));
			}
			out.add(list);
		}
		InfoDto info=(InfoDto)obj;
		List<Integer> list=plantMapper.getPortInfo(info.getApCode(), info.getSfCode());
		for (Integer index : list) {
			out.get(index/8).get(index%8).setStatus(true);
		}
		
		return ResultDto.createInstance(true).setData(out);
	}
	
}
