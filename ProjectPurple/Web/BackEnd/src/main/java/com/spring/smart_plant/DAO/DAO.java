package com.spring.smart_plant.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.spring.smart_plant.common.utills.ConstantJDBCTemplate;
import com.spring.smart_plant.device.domain.APInfoDTO;
import com.spring.smart_plant.device.domain.SmartFarmInfoDTO;
import com.spring.smart_plant.user.domain.LoginDTO;
import com.spring.smart_plant.user.domain.UserInfoDTO;

public class DAO {
	JdbcTemplate template;
	
	public DAO() {
		template=ConstantJDBCTemplate.getTemplate();
	}

	//로그인할때 사용자 존재 여부 확인하기 위해 사용
	public UserInfoDTO searchMember(LoginDTO dto) {
		UserInfoDTO resultDto=null;
		String query="SELECT * FROM PLANT_USER WHERE PWD=? AND EMAIL=?";
		Object[] params= {dto.getPwd(), dto.getEmail()};
		int[] types= {Types.VARCHAR, Types.VARCHAR};
		try {
			resultDto=template.queryForObject(query,params,types,new BeanPropertyRowMapper<UserInfoDTO>(UserInfoDTO.class));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultDto;
	}
	
	//메인페이지에서 사용자의 수경재배기정보들을 알아내기 위해서 사용
	public ArrayList<SmartFarmInfoDTO> getAllSmartPlant(int userCode){
		String query="SELECT * FROM SF WHERE USER_CODE=?";
		Object[] params= {userCode};
		int[] types= {Types.INTEGER};
		return (ArrayList<SmartFarmInfoDTO>) template.query(query,params,types,new BeanPropertyRowMapper<SmartFarmInfoDTO>(SmartFarmInfoDTO.class));
	}
	
	//회원가입 페이지에서 사용
	public void insertMember(UserInfoDTO dto) {
		String query = "INSERT INTO PLANT_USER(USER_CODE,PWD,EMAIL,FIRST_NAME,SECOND_NAME,SF_CNT) VALUES(PLANT_USER_SEQ.NEXTVAL,?,?,?,?,0)";
		template.update(query,new PreparedStatementSetter() {
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

	//회원가입 페이지에서 사용
	public int searchMember(String email) {
		String query="SELECT * FROM PLANT_USER WHERE EMAIL=?";
		Object[] params= {email};
		int[] types= {Types.VARCHAR};
		System.out.println(email);
		return template.query(query, params,types,new BeanPropertyRowMapper<UserInfoDTO>(UserInfoDTO.class)).size();
		//결과가 존재한다면 1반환
	}
	
	//AP등록시에 사용
	public void insertAP(APInfoDTO dto) {
		String query="INSERT INTO AP(AP_PUBLIC_IP,AP_SSID) VALUES(?,?)";
		System.out.println("insert ap");
		template.update(query,new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement arg0) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setString(1, dto.getApPublicIp());
				arg0.setString(2, dto.getApSsid());
			}
		});
	}
	
	public void deleteAP(String apPublicIp) {
		String query="DELETE FROM AP WHERE AP_PUBLIC_IP=?";
		System.out.println("delete ap");
		template.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement arg0) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setString(1, apPublicIp);
			}
		});
	}
	
	//SF장비 추가, SF_SEQ로 SF코드생성
	public void insertSmartFarmDevice(String ip, String innerIP, int userCode) {
		String query="INSERT INTO SF (SF_CODE,SF_PORT_CNT, FLOOR_CNT,USER_CODE,COOLER_CNT,LED_CTRL_MODE,"
				+ "TEMP_DELAY,HUMI_DELAY,ELUM_DELAY,WATER_TEMP_DELAY,WATER_LIM_DELAY,INNER_IP,AP_PUBLIC_IP) "
				+ "VALUES (SF_SEQ.NEXTVAL,32,2,?,3,'Y',60,60,60,60,60,?,?)";
		System.out.println("insert device");
		template.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement arg0) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setInt(1, userCode);
				arg0.setString(2, innerIP);
				arg0.setString(3, ip);
			}
		});
	}
	
	public void deleteSmartFarmDevice(int sfCode) {
		String query="DELETE FROM SF WHERE SF_CODE=?";
		System.out.println("delete device");
		template.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement arg0) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setInt(1, sfCode);
			}
		});
	}
	
	/*public void insertData(SensorDataDTO dto) {
		String query="INSERT INTO SENSOR_DATA (CALC_DT,SF_CODE,TEMP,HUMI,ELUM,WATER_TEMP,WATER_LIM) "
				+ "VALUES (?,?,?,?,?,?,?)";
		template.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement arg0) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setTimestamp(1, dto.getDate());
				arg0.setInt(2, dto.getSfCode());
				arg0.setInt(3, dto.getTemp());
				arg0.setInt(4, dto.getHumi());
				arg0.setInt(5, dto.getElum());
				arg0.setInt(6, dto.getWaterTemp());
				arg0.setInt(7, dto.getWaterLim());
			}
		});
	}*/
}

