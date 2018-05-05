package com.spring.smart_plant.device.domain;

import java.util.List;

public class APStatusDTO {
	private int code;
	private List<Object> innerIp;
	/**
	 * 
	 */
	public APStatusDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param code
	 */
	public APStatusDTO(int code) {
		super();
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<Object> getInnerIp() {
		return innerIp;
	}

	public void setInnerIp(List<Object> innerIp) {
		this.innerIp = innerIp;
	}


	
}
