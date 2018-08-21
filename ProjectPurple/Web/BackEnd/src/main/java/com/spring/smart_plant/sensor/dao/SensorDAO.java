package com.spring.smart_plant.sensor.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.smart_plant.common.domain.DateSearchDTO;
import com.spring.smart_plant.sensor.domain.SensorDataDTO;

@Repository
public class SensorDAO {
	
	private final String namespace="sensor";
	
	@Autowired
	private SqlSessionTemplate sql;
	
	public void insertData(HashMap<String, Object> map) {
		sql.insert(namespace+".insertData",map);
	}
	
	public  SensorDataDTO getLatestData(int sfCode, int apCode) {
		HashMap<String, Integer> map=new HashMap<>();
		map.put("sfCode", sfCode);
		map.put("apCode", apCode);
		return sql.selectOne(namespace+".getLatestData",map);
	}
	
	public List<SensorDataDTO> getDayData(DateSearchDTO dto){
		return sql.selectList(namespace+".getDayData",dto);
	}
	
	public void deleteAllSensorData(HashMap<String, Integer> map) {
		sql.delete(namespace+".deleteAllSensorData", map);
	}
}
