package com.spring.smart_plant.log.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.smart_plant.common.domain.DateSearchDTO;
import com.spring.smart_plant.log.domain.DeviceLogDTO;

@Repository
public class LogDAO {
	
	@Autowired
	private SqlSessionTemplate sql;
	
	private final String namespace="log";
	
	public void insertLog(DeviceLogDTO dto) {
		System.out.println(dto.toString());
		sql.insert(namespace+".insertLog",dto);
	}
	
	public List<DeviceLogDTO> getLog(DateSearchDTO dto){
		System.out.println(dto.getDate());
		return sql.selectList(namespace+".getLog", dto);
	}
	
	/**
	 * <pre>
	 * 특정 공유기에 연결된 수경재배기들의 로그들 삭제
	 * </pre>
	 * @param ip
	 * @return
	 */
	public void deleteAllLog(int apCode) {
		sql.delete(namespace+".deleteAllLog", apCode);
	}
	
	
	/**
	 * <pre>
	 * 한개의 수경재배기에 대한 로그들 삭제
	 * </pre>
	 * @param sfCode
	 * @return
	 */
	public int deleteSingleLog(int sfCode, int apCode) {
		HashMap<String, Integer> map=new HashMap<>();
		map.put("sfCode", sfCode);
		map.put("apCode", apCode);
		return sql.delete(namespace+".deleteSingleLog", map);
	}
}
