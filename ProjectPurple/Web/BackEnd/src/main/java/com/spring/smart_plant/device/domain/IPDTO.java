package com.spring.smart_plant.device.domain;

public class IpDTO {
	private String ip;

	/**
	 * 
	 */
	public IpDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param innerIp
	 */
	public IpDTO(String ip) {
		super();
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
