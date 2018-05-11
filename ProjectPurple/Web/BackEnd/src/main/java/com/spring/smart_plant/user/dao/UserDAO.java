package com.spring.smart_plant.user.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDAO {
	@Autowired
	private SqlSession sql;
	
	private static final String namespace="user";
	
	public void incrementSfCount(int userCode) {
		sql.update(namespace+".incrementSfCount", userCode);
	}
	
	public void decrementSfCount(int count,int userCode) {
		HashMap<String, Object> map=new HashMap<>();
		map.put("count", count);
		map.put("userCode", userCode);
		sql.update(namespace+".incrementSfCount", map);
	}
}
