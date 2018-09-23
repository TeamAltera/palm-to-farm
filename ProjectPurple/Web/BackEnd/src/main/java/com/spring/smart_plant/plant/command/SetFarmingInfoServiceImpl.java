package com.spring.smart_plant.plant.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.smart_plant.plant.dao.PlantDAO;
import com.spring.smart_plant.plant.domain.PortStatusDTO;

@Service("setFarmingInfoService")
public class SetFarmingInfoServiceImpl{

	@Autowired
	private PlantDAO dao;
	
	public void execute(Object farm, int apCode, int sfCode) throws Exception {
		// TODO Auto-generated method stub

		List<Map<String, Object>> portList = new ArrayList<Map<String, Object>>();
		int cnt=0;
		@SuppressWarnings("unchecked")
		List<List<PortStatusDTO>> farming=(List<List<PortStatusDTO>>) farm;
		for (List<PortStatusDTO> list : farming) {
			for (PortStatusDTO portStatus : list) {
				if(portStatus.getStatus()) {
					HashMap<String, Object> map=new HashMap<>();
					map.put("portNo", cnt);
					map.put("portSt", 'T');
					portList.add(map);
				}
				cnt++;
			}
		}
		System.out.println(portList.size());
		dao.insertPortInfo(apCode, sfCode, portList);
	}

}
