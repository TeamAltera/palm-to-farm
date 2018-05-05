package com.spring.smart_plant.device.domain;

public class APInfoDTO {
	private String apPublicIp;
	private String apSsid;
	private int userCode;

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
	public APInfoDTO(String apPublicIp, String apSsid, int userCode) {
		super();
		this.apPublicIp = apPublicIp;
		this.apSsid = apSsid;
		this.userCode=userCode;
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
	
}
