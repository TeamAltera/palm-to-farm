package com.spring.smart_plant.sensor.dao;

import java.security.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.smart_plant.sensor.domain.SensorDataDTO;

@Repository
public class SensorDAO {
	
	private static final String namespace="sensor";
	
	@Autowired
	private SqlSessionTemplate sql;
	
	public void insertData(HashMap<String, Object> map) {
		sql.insert(namespace+".insertData",map);
	}
	
	public  SensorDataDTO getLatestData(int sfCode) {
		return sql.selectOne(namespace+".getLatestData",sfCode);
	}
	
	public List<SensorDataDTO> getDayData(Timestamp date){
		return null;
	}
}
