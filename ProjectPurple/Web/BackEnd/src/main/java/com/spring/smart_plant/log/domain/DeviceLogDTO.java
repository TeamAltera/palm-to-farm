package com.spring.smart_plant.log.domain;

import java.sql.Timestamp;

public class DeviceLogDTO {

	private Timestamp usedDate;
	private int sfCode;
	private int apCode;
	private String usedIp; //명령을 내린 사용자Ip주소
	private String actName;
	private char result;
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
	 * @param usedIp
	 * @param actName
	 * @param result
	 */
	public DeviceLogDTO(Timestamp usedDate, int sfCode, int apCode, String usedIp, String actName,
			char result) {
		super();
		this.usedDate = usedDate;
		this.sfCode = sfCode;
		this.apCode = apCode;
		this.usedIp = usedIp;
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
	public int getApCode() {
		return apCode;
	}
	public void setApCode(int apCode) {
		this.apCode = apCode;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getUsedIp() {
		return usedIp;
	}
	public void setUsedIp(String usedIp) {
		this.usedIp = usedIp;
	}
	public char getResult() {
		return result;
	}
	public void setResult(char result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "dt:"+usedDate+" sf:"+sfCode+" ap:"+apCode+" ip:"+usedIp+" act:"+actName+" res:"+result;
	}
}
