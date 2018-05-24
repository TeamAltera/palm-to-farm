package com.spring.smart_plant.log.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.smart_plant.log.domain.DeviceLogDTO;

@Repository
public class LogDAO {
	
	@Autowired
	private SqlSessionTemplate sql;
	
	private final String namespace="log";
	
	public void insertLog(DeviceLogDTO dto) {
		sql.insert(namespace+".insertLog",dto);
	}
}
