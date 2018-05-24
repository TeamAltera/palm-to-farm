package com.spring.smart_plant.log.command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.log.dao.LogDAO;
import com.spring.smart_plant.log.domain.DeviceLogDTO;
import com.spring.smart_plant.log.domain.DeviceLogSearchDTO;

@Service("getLogService")
public class GetLogServiceImpl implements ILogService{

	@Autowired
	private LogDAO dao;
	
	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		DeviceLogSearchDTO dto=(DeviceLogSearchDTO)obj;
		List<DeviceLogDTO> result=dao.getLog(dto);
		return ResultDTO.createInstance(true)
				.setMsg(dto.getDate()+"의 사용기록은 "+result.size()+"개 입니다.")
				.setData(result);
	}
}
