package com.spring.smart_plant.common.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.spring.smart_plant.common.utills.ConstantJDBCTemplate;
import com.spring.smart_plant.device.domain.APInfoDTO;
import com.spring.smart_plant.device.domain.SmartFarmInfoDTO;
import com.spring.smart_plant.sensor.domain.SensorDataDTO;
import com.spring.smart_plant.user.domain.LoginDTO;
import com.spring.smart_plant.user.domain.UserInfoDTO;

public class DAO {
	JdbcTemplate template;

	public DAO() {
		template = ConstantJDBCTemplate.getTemplate();
	}

	// 로그인할때 사용자 존재 여부 확인하기 위해 사용
	public UserInfoDTO searchMember(LoginDTO dto) {
		String query = "SELECT * FROM PLANT_USER WHERE PWD=? AND EMAIL=?";
		Object[] params = { dto.getPwd(), dto.getEmail() };
		int[] types = { Types.VARCHAR, Types.VARCHAR };
		try {
			return (UserInfoDTO) template.queryForObject(query, params, types,
					new BeanPropertyRowMapper<UserInfoDTO>(UserInfoDTO.class));
		} catch (EmptyResultDataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	// 회원가입 페이지에서 사용
	public void insertMember(UserInfoDTO dto) {
		String query = "INSERT INTO PLANT_USER(USER_CODE,PWD,EMAIL,FIRST_NAME,SECOND_NAME,SF_CNT) VALUES(PLANT_USER_SEQ.NEXTVAL,?,?,?,?,0)";
		template.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement arg0) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setString(1, dto.getPwd());
				arg0.setString(2, dto.getEmail());
				arg0.setString(3, dto.getFirstName());
				arg0.setString(3, dto.getSecondName());
			}
		});
	}

	// 회원가입 페이지에서 사용
	public int searchMember(String email) {
		String query = "SELECT * FROM PLANT_USER WHERE EMAIL=?";
		Object[] params = { email };
		int[] types = { Types.VARCHAR };
		return template.query(query, params, types, new BeanPropertyRowMapper<UserInfoDTO>(UserInfoDTO.class)).size();
		// 결과가 존재한다면 1반환
	}

	// DB에 센싱 데이터 저장
	public void insertData(SensorDataDTO dto) {
		String query = "INSERT INTO SENSOR_DATA (CALC_DT,SF_CODE,TEMP,HUMI,ELUM,WATER_TEMP,WATER_LIM) "
				+ "VALUES (?,?,?,?,?,?,?)";
		template.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement arg0) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setTimestamp(1, dto.getDate());
				arg0.setInt(2, dto.getSfCode());
				arg0.setDouble(3, dto.getTemp());
				arg0.setDouble(4, dto.getHumi());
				arg0.setInt(5, dto.getElum());
				arg0.setDouble(6, dto.getWaterTemp());
				arg0.setDouble(7, dto.getWaterLim());
			}
		});
	}
}
