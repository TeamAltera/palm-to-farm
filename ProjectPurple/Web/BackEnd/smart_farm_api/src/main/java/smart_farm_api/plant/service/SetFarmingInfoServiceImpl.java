package smart_farm_api.plant.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smart_farm_api.plant.domain.PortStatusDto;
import smart_farm_api.plant.repository.PlantMapper;

@Service
@AllArgsConstructor
public class SetFarmingInfoServiceImpl{

	private PlantMapper plantMapper;
	
	public void execute(Object farm, int apCode, int stamp) throws Exception {
		// TODO Auto-generated method stub

		List<Map<String, Object>> portList = new ArrayList<Map<String, Object>>();
		int cnt=0;
		@SuppressWarnings("unchecked")
		List<List<PortStatusDto>> farming=(List<List<PortStatusDto>>) farm;
		for (List<PortStatusDto> list : farming) {
			for (PortStatusDto portStatus : list) {
				if(portStatus.isStatus()) {
					HashMap<String, Object> map=new HashMap<>();
					map.put("portNo", cnt);
					map.put("portSt", 'T');
					portList.add(map);
				}
				cnt++;
			}
		}
		System.out.println(portList.size());
		plantMapper.insertPortInfo(apCode, stamp, portList);
	}

}
