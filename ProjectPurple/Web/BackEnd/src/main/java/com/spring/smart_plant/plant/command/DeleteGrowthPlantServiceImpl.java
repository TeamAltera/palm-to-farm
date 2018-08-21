package com.spring.smart_plant.plant.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.plant.dao.PlantDAO;
import com.spring.smart_plant.plant.domain.InfoDTO;

@Service("deleteGrowthPlantService")
public class DeleteGrowthPlantServiceImpl implements IPlantService{
	
	@Autowired
	private PlantDAO plantDao;
	
	@Override
	public ResultDTO execute(Object obj) throws Exception {
		// TODO Auto-generated method stub
		InfoDTO info=(InfoDTO)obj;
		System.out.println(info.getApCode()+"  "+info.getSfCode());
		plantDao.deleteGrowthPlant(info.getApCode(), info.getSfCode());
		return null;
	}

}
