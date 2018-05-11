package com.spring.smart_plant.device.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.smart_plant.device.domain.APInfoDTO;
import com.spring.smart_plant.device.domain.SmartFarmInfoDTO;

@Repository
public class DeviceDAO {
	
	@Autowired
	private SqlSession sql;
	
	private static final String namespace="device";
	
	public List<APInfoDTO> getAllAP(int userCode){
		return sql.selectList(namespace+".getAllAP", userCode);
	}
	
	public boolean getAP(String ip) {
		return sql.selectOne(namespace+".getAP", ip)!=null;
	}
	
	public List<SmartFarmInfoDTO> getAllSmartPlant(int userCode){
		return sql.selectList(namespace+".getAllSmartPlant", userCode);
	}
	
	public void insertAP(APInfoDTO dto) {
		sql.insert(namespace+".insertAP", dto);
	}
	
	public void deleteAP(String apPublicIp) {
		sql.delete(namespace+".deleteAP",apPublicIp);
	}
	
	public void insertSmartFarmDevice(String innerIp, int userCode, String ip) {
		HashMap<String, Object> map=new HashMap<>();
		map.put("innerIp", innerIp);
		map.put("userCode", userCode);
		map.put("ip", ip);
		sql.insert(namespace+".insertSmartFarmDevice", map);
	}
	
	public void deleteSmartFarmDevice(int sfCode) {
		sql.delete(namespace+".deleteSmartFarmDevice",sfCode);
	}
	
	public int deleteSmartFarmAPAllDevice(String apPublicIp) {
		return sql.delete(namespace+".deleteSmartFarmAPAllDevice",apPublicIp);
	}
}
