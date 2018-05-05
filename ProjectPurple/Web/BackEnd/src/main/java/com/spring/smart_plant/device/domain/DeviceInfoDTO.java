package com.spring.smart_plant.device.domain;

public class DeviceInfoDTO {
	APInfoDTO apInfo;
	IPDTO ipInfo;
	
	/**
	 * 
	 */
	public DeviceInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public APInfoDTO getApInfo() {
		return apInfo;
	}
	/**
	 * @param apInfo
	 * @param ipInfo
	 */
	public DeviceInfoDTO(APInfoDTO apInfo, IPDTO ipInfo) {
		super();
		this.apInfo = apInfo;
		this.ipInfo = ipInfo;
	}
	public void setApInfo(APInfoDTO apInfo) {
		this.apInfo = apInfo;
	}
	public IPDTO getIpInfo() {
		return ipInfo;
	}
	public void setIpInfo(IPDTO ipInfo) {
		this.ipInfo = ipInfo;
	}
	
	
}
