package com.spring.smart_plant.plant.command;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.plant.dao.PlantDAO;
import com.spring.smart_plant.plant.domain.InfoDTO;
import com.spring.smart_plant.plant.domain.PortStatusDTO;

@Service("getPortInfoService")
public class GetPortInfoServiceImpl implements IPlantService{

	@Autowired
	PlantDAO dao;
	
	@Override
	public ResultDTO execute(Object obj) throws Exception {
		// TODO Auto-generated method stub
		List<List<PortStatusDTO>> out=new ArrayList<>();
		for(int i=0; i<4; i++) {
			List<PortStatusDTO> list=new ArrayList<>();
			for (int j = 0; j < 8; j++) {
				list.add(new PortStatusDTO(false));
			}
			out.add(list);
		}
		InfoDTO info=(InfoDTO)obj;
		List<Integer> list=dao.getPortInfo(info.getApCode(), info.getSfCode());
		for (Integer index : list) {
			out.get(index/8).get(index%8).setStatus(true);
		}
		
		return ResultDTO.createInstance(true).setData(out);
	}
	
}
