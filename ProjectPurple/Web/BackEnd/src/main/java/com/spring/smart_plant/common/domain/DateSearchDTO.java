package com.spring.smart_plant.common.domain;

public class DateSearchDTO {
	private String date; //조회하고자 하는 날짜(YYYY-MM-DD)
	private int sfCode;//조회 하고자 하는 수경재배기 코드
	/**
	 * @param date
	 * @param sfCode
	 */
	public DateSearchDTO(String date, int sfCode) {
		super();
		this.date = date;
		this.sfCode = sfCode;
	}
	public DateSearchDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getSfCode() {
		return sfCode;
	}
	public void setSfCode(int sfCode) {
		this.sfCode = sfCode;
	}
}
