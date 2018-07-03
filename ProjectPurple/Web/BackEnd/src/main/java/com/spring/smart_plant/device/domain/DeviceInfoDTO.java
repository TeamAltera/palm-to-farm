package com.spring.smart_plant.device.domain;

public class DeviceInfoDTO {
	private int apCode;
	private int sfCode;
	private String ipInfo;
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
	public DeviceInfoDTO(int apCode, int sfCode, String ipInfo) {
		super();
		this.apCode = apCode;
		this.sfCode = sfCode;
		this.ipInfo = ipInfo;
	}
	
	public int getApCode() {
		return apCode;
	}
	public void setApCode(int apCode) {
		this.apCode = apCode;
	}
	public String getIpInfo() {
		return ipInfo;
	}
	public void setIpInfo(String ipInfo) {
		this.ipInfo = ipInfo;
	}
	public int getSfCode() {
		return sfCode;
	}
	public void setSfCode(int sfCode) {
		this.sfCode = sfCode;
	}
}
