package com.spring.smart_plant.device.domain;

public class IPDTO {
	private String innerIp;

	/**
	 * 
	 */
	public IPDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param innerIp
	 */
	public IPDTO(String innerIp) {
		super();
		this.innerIp = innerIp;
	}

	public String getInnerIp() {
		return innerIp;
	}

	public void setInnerIp(String innerIp) {
		this.innerIp = innerIp;
	}
}
