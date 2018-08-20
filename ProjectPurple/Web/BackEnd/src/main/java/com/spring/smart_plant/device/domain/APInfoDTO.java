package com.spring.smart_plant.device.domain;

import java.sql.Timestamp;

public class APInfoDTO {
	private int apCode;
	private String apPublicIp;
	private String apSsid;
	private int userCode;
	private int apSfCnt;
	private Timestamp apRegDate;

	/**
	 * 
	 */
	public APInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param apPublicIp
	 * @param apSsid
	 */
	public APInfoDTO(String apPublicIp, String apSsid, int userCode, int apSfCnt) {
		super();
		this.apPublicIp = apPublicIp;
		this.apSsid = apSsid;
		this.userCode=userCode;
		this.apSfCnt=apSfCnt;
	}

	public int getApCode() {
		return apCode;
	}

	public void setApCode(int apCode) {
		this.apCode = apCode;
	}

	public String getApPublicIp() {
		return apPublicIp;
	}
	public void setApPublicIp(String apPublicIp) {
		this.apPublicIp = apPublicIp;
	}
	public String getApSsid() {
		return apSsid;
	}
	public void setApSsid(String apSsid) {
		this.apSsid = apSsid;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public int getApSfCnt() {
		return apSfCnt;
	}

	public void setApSfCnt(int apSfCnt) {
		this.apSfCnt = apSfCnt;
	}

	public Timestamp getApRegDate() {
		return apRegDate;
	}

	public void setApRegDate(Timestamp apRegDate) {
		this.apRegDate = apRegDate;
	}
	
}
