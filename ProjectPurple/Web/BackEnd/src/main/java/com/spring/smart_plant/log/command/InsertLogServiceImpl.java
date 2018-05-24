package com.spring.smart_plant.log.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.log.dao.LogDAO;
import com.spring.smart_plant.log.domain.DeviceLogDTO;

@Service("insertLogService")
public class InsertLogServiceImpl implements ILogService{
	
	@Autowired
	private LogDAO dao;
	
	@Override
	public ResultDTO execute(Object obj) {
		dao.insertLog((DeviceLogDTO)obj);//Y이면 성공 N이면 실패
		return null;
	}

}
