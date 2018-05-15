package com.spring.smart_plant.log.domain;

import java.sql.Timestamp;

public class DeviceLogDTO {
	private Timestamp usedDate;
	private int sfCode;
	private String apPublicIp;
	private String orderIp; //명령을 내린 사용자Ip주소
	private String actName;
	private String result;
	/**
	 * 
	 */
	public DeviceLogDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param usedDate
	 * @param sfCode
	 * @param apPublicIp
	 * @param orderIp
	 * @param actName
	 * @param result
	 */
	public DeviceLogDTO(Timestamp usedDate, int sfCode, String apPublicIp, String orderIp, String actName,
			String result) {
		super();
		this.usedDate = usedDate;
		this.sfCode = sfCode;
		this.apPublicIp = apPublicIp;
		this.orderIp = orderIp;
		this.actName = actName;
		this.result = result;
	}
	public Timestamp getUsedDate() {
		return usedDate;
	}
	public void setUsedDate(Timestamp usedDate) {
		this.usedDate = usedDate;
	}
	public int getSfCode() {
		return sfCode;
	}
	public void setSfCode(int sfCode) {
		this.sfCode = sfCode;
	}
	public String getApPublicIp() {
		return apPublicIp;
	}
	public void setApPublicIp(String apPublicIp) {
		this.apPublicIp = apPublicIp;
	}
	public String getOrderIp() {
		return orderIp;
	}
	public void setOrderIp(String orderIp) {
		this.orderIp = orderIp;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
