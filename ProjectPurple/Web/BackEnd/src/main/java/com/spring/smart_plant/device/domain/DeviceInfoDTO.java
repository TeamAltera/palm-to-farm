package com.spring.smart_plant.device.domain;

public class DeviceInfoDTO {
	private String apInfo;
	private String ipInfo;
	private int userCode;
	/**
	 * 
	 */
	public DeviceInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param apInfo
	 * @param ipInfo
	 */
	public DeviceInfoDTO(String apInfo, String ipInfo,int userCode) {
		super();
		this.apInfo = apInfo;
		this.ipInfo = ipInfo;
		this.userCode=userCode;
	}
	
	public String getApInfo() {
		return apInfo;
	}
	public void setApInfo(String apInfo) {
		this.apInfo = apInfo;
	}
	public String getIpInfo() {
		return ipInfo;
	}
	public void setIpInfo(String ipInfo) {
		this.ipInfo = ipInfo;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	
}
