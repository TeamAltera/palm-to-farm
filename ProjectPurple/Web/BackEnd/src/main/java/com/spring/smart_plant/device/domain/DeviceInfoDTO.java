package com.spring.smart_plant.device.domain;

public class DeviceInfoDTO {
	private String ip;
	private String ssid;
	private String[] innerIp;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String[] getInnerIp() {
		return innerIp;
	}
	public void setInnerIp(String[] innerIp) {
		this.innerIp = innerIp;
	}
}
