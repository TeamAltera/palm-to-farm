package com.spring.smart_plant.log.domain;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeviceLogSearchDTO {
	private Timestamp date; //조회하고자 하는 날짜
	private int sfCode;//조회 하고자 하는 수경재배기 코드
	/**
	 * @param date
	 * @param sfCode
	 */
	public DeviceLogSearchDTO(String date, int sfCode) {
		super();
		this.date = parseStringDate(date);
		this.sfCode = sfCode;
	}
	/**
	 * 
	 */
	public DeviceLogSearchDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = parseStringDate(date);
	}
	public int getSfCode() {
		return sfCode;
	}
	public void setSfCode(int sfCode) {
		this.sfCode = sfCode;
	}
	private Timestamp parseStringDate(String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date parseDate=sdf.parse(dateFormat);
			return new Timestamp(parseDate.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
