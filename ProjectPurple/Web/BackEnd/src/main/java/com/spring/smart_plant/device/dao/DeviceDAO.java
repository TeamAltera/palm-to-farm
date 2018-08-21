package com.spring.smart_plant.device.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.smart_plant.device.domain.APInfoDTO;
import com.spring.smart_plant.device.domain.SmartFarmInfoDTO;

@Repository
public class DeviceDAO{
	
	@Autowired
	private SqlSessionTemplate sql;
	
	private final String namespace="device";
	
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
	
	public Object getSmartPlant(int apCode,int sfCode) {
		HashMap<String, Object> map=new HashMap<>();
		map.put("sfCode", sfCode);
		map.put("apCode", apCode);
		return sql.selectOne(namespace+".getSmartPlant", map);
	}
	
	//AP등록
	public int insertAP(APInfoDTO dto) {
		//Object -> map
		ObjectMapper oMapper = new ObjectMapper();
        @SuppressWarnings("unchecked")
		Map<String, Object> map = oMapper.convertValue(dto, Map.class);
        sql.insert(namespace+".insertAP", map);
		return (int)map.get("id");
	}
	
	//AP삭제
	public void deleteAP(int apCode) {
		sql.delete(namespace+".deleteAP",apCode);
	}
	
	//AP에 등록된 SF 갯수 업데이트
	public void updateSFCount(int apCode) {
		sql.update(namespace+".updateSFCount",apCode);
	}
	
	// SF장비 추가, SF_SEQ로 SF코드생성
	public void insertSmartFarmDeviceList(List<Map<String, Object>> deviceList, int apCode) throws Exception{
		HashMap<String, Object> map=new HashMap<>();
		map.put("deviceList", deviceList);//각 요소는 sfCode, innerIp로 구성
		map.put("apCode", apCode);
		try {
			sql.insert(namespace+".insertSmartFarmDeviceList", map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	public void insertSmartFarmDevice(int sfCode, String innerIp, int apCode) throws Exception{
		HashMap<String, Object> map=new HashMap<>();
		map.put("sfCode", sfCode);
		map.put("innerIp", innerIp);
		map.put("apCode", apCode);
		try {
			sql.insert(namespace+".insertSmartFarmDevice", map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	public int getUserCodeOfAP(int apCode) {
		return sql.selectOne(namespace+".getUserCodeOfAP", apCode);
	}
	
	//수경재비기 한 대 삭제
	public void deleteSmartFarmDevice(int sfCode) {
		sql.delete(namespace+".deleteSmartFarmDevice",sfCode);
	}
	
	//공유기에 연결된 모든 수경재배기 삭제
	public int deleteSmartFarmAPAllDevice(int apCode) throws Exception{
		int count=0;
		try {
			count=sql.delete(namespace+".deleteSmartFarmAPAllDevice",apCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return count;
	}
	
	public void updatePort(int apCode, int sfCode, int sfPort, char pumpSt) {
		HashMap<String, Object> map=new HashMap<>();
		map.put("sfCode", sfCode);
		map.put("sfPort", sfPort);
		map.put("apCode", apCode);
		map.put("pumpSt", pumpSt);
		sql.update(namespace+".updatePort", map);
	}
	
	public String getApIp(int apCode) {
		return sql.selectOne(namespace+".getApIp", apCode);
	}
}