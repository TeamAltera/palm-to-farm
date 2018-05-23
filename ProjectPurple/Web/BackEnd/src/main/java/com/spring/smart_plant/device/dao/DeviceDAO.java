package com.spring.smart_plant.device.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.smart_plant.device.domain.APInfoDTO;
import com.spring.smart_plant.device.domain.SmartFarmInfoDTO;
import com.spring.smart_plant.user.dao.UserDAO;

@Repository
public class DeviceDAO{
	
	@Autowired
	private SqlSessionTemplate sql;
	
	@Autowired
	private UserDAO userDao;
	
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
	
	//AP등록
	public void insertAP(APInfoDTO dto) {
		sql.insert(namespace+".insertAP", dto);
	}
	
	//AP삭제
	public void deleteAP(String apPublicIp) {
		sql.delete(namespace+".deleteAP",apPublicIp);
	}
	
	// SF장비 추가, SF_SEQ로 SF코드생성
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void insertSmartFarmDevice(String innerIp, int userCode, String ip) throws Exception{
		HashMap<String, Object> map=new HashMap<>();
		map.put("innerIp", innerIp);
		map.put("userCode", userCode);
		map.put("ip", ip);
		try {
			sql.insert(namespace+".insertSmartFarmDevice", map);
			userDao.incrementSfCount(userCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}
	
	//수경재비기 한 대 삭제
	public void deleteSmartFarmDevice(int sfCode) {
		sql.delete(namespace+".deleteSmartFarmDevice",sfCode);
	}
	
	//공유기에 연결된 모든 수경재배기 삭제
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public int deleteSmartFarmAPAllDevice(String apPublicIp, int userCode) throws Exception{
		int count=0;
		try {
			count=sql.delete(namespace+".deleteSmartFarmAPAllDevice",apPublicIp);
			userDao.decrementSfCount(count, userCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return count;
	}
}