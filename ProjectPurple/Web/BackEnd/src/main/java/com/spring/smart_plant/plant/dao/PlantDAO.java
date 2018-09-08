package com.spring.smart_plant.plant.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.smart_plant.plant.domain.PlantDTO;

@Repository
public class PlantDAO {
	@Autowired
	private SqlSessionTemplate sql;

	private final String namespace = "plant";

	// 사용자의 모든 AP정보 알아내기 위해서 사용
	public PlantDTO getPlantInfo(int apCode, int sfCode) {
		HashMap<String, Object> map=new HashMap<>();
		map.put("sfCode", sfCode);
		map.put("apCode", apCode);
		return (PlantDTO)sql.selectOne(namespace + ".getPlantInfo", map);
	}
	
	public void insertGrowthPlant(int apCode, int sfCode, int plantCode) {
		HashMap<String, Object> map=new HashMap<>();
		map.put("sfCode", sfCode);
		map.put("apCode", apCode);
		map.put("plantCode", plantCode);
		sql.insert(namespace + ".insertGrowthPlant", map);
	}
	
	public void deleteGrowthPlant(int apCode, int sfCode) {
		HashMap<String, Object> map=new HashMap<>();
		map.put("sfCode", sfCode);
		map.put("apCode", apCode);
		sql.delete(namespace + ".deleteGrowthPlant", map);
	}
	
	public void deletePortInfo(int apCode, int sfCode) {
		HashMap<String, Object> map=new HashMap<>();
		map.put("sfCode", sfCode);
		map.put("apCode", apCode);
		sql.delete(namespace + ".deletePortInfo", map);
	}
	
	public void insertPortInfo(int apCode, int sfCode, List<Map<String, Object>> portList) {
		HashMap<String, Object> map=new HashMap<>();
		map.put("sfCode", sfCode);
		map.put("apCode", apCode);
		map.put("portList", portList);
		sql.insert(namespace + ".insertPortInfo", map);
	}
	
	public List<Integer> getPortInfo(int apCode, int sfCode) {
		HashMap<String, Object> map=new HashMap<>();
		map.put("sfCode", sfCode);
		map.put("apCode", apCode);
		return sql.selectList(namespace + ".getPortInfo", map);
	}
}