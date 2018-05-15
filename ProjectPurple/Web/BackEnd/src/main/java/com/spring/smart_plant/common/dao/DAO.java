package com.spring.smart_plant.common.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.spring.smart_plant.common.utills.ConstantJDBCTemplate;
import com.spring.smart_plant.sensor.domain.SensorDataDTO;

public class DAO {
	JdbcTemplate template;

	public DAO() {
		template = ConstantJDBCTemplate.getTemplate();
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
