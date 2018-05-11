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
	
	//사용자의 모든 AP정보 알아내기 위해서 사용
	public List<APInfoDTO> getAllAP(int userCode){
		return sql.selectList(namespace+".getAllAP", userCode);
	}
	
	// AP가 등록되어있는지를 조회
	public boolean getAP(String ip) {
		return sql.selectOne(namespace+".getAP", ip)!=null;
	}
	
	//사용자의 모든 수경재배기 알아내기 위해서 사용
	public List<SmartFarmInfoDTO> getAllSmartPlant(int userCode){
		return sql.selectList(namespace+".getAllSmartPlant", userCode);
	}
	
	//AP등록
	public void insertAP(APInfoDTO dto) {
		sql.insert(namespace+".insertAP", dto);
	}
	
	//AP삭제
	public void deleteAP(String apPublicIp) {
		sql.delete(namespace+".deleteAP",apPublicIp);
	}
	
	// SF장비 추가, SF_SEQ로 SF코드생성
	public void insertSmartFarmDevice(String innerIp, int userCode, String ip) {
		HashMap<String, Object> map=new HashMap<>();
		map.put("innerIp", innerIp);
		map.put("userCode", userCode);
		map.put("ip", ip);
		sql.insert(namespace+".insertSmartFarmDevice", map);
	}
	
	//수경재비기 한 대 삭제
	public void deleteSmartFarmDevice(int sfCode) {
		sql.delete(namespace+".deleteSmartFarmDevice",sfCode);
	}
	
	//공유기에 연결된 모든 수경재배기 삭제
	public int deleteSmartFarmAPAllDevice(String apPublicIp) {
		return sql.delete(namespace+".deleteSmartFarmAPAllDevice",apPublicIp);
	}
}
