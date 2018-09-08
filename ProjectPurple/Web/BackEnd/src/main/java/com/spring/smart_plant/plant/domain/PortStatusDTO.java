package com.spring.smart_plant.plant.domain;

public class PortStatusDTO {
	private boolean status;
	
	/**
	 * 
	 */
	public PortStatusDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param status
	 */
	public PortStatusDTO(boolean status) {
		super();
		this.status = status;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
