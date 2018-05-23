package com.spring.smart_plant.user.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.smart_plant.user.domain.LoginDTO;
import com.spring.smart_plant.user.domain.UserInfoDTO;

@Repository
public class UserDAO {
	@Autowired
	private SqlSessionTemplate sql;
	
	private static final String namespace="user";
	
	//수경재배기 추가시 사용자의 수경재배기 보유 갯수 증가
	public void incrementSfCount(int userCode) {
		sql.update(namespace+".incrementSfCount", userCode);
	}
	
	//수경재배기 삭제시 사용자의 수경재배기 보유 갯수 감소
	public void decrementSfCount(int count,int userCode) {
		HashMap<String, Object> map=new HashMap<>();
		map.put("count", count);
		map.put("userCode", userCode);
		sql.update(namespace+".decrementSfCount", map);
	}
	
	//로그인 시 사용자 존재 유무 조회
	public UserInfoDTO searchMember(LoginDTO dto) {
		System.out.println("st");
		return sql.selectOne(namespace+".searchMember",dto);
	}
	
	//로그인 실패시 해당 계정 블락 횟수 증가
	public void incrementBlockCount(String email) {
		sql.update(namespace+".incrementBlockCount",email);
	}
	
	public void initBlockCount(int userCode) {
		sql.update(namespace+".initBlockCount",userCode);
	}
	
	public UserInfoDTO getMemberInfo(int userCode) {
		return sql.selectOne(namespace+".getMemberInfo",userCode);
	}
	//회원가입
	public void insertMember(UserInfoDTO dto) {
		sql.insert(namespace+".insertMember",dto);
	}
	
	//이메일 존재 유무 조회
	public int searchEmail(String email) {
		//결과가 있다면 1 반환
		return sql.selectList(namespace+".searchEmail",email).size();
	}
}
