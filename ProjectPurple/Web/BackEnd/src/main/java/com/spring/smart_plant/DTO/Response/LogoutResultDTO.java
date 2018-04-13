package com.spring.smart_plant.DTO.Response;

public class LogoutResultDTO {
	private boolean result;
	
	/**
	 * 
	 */
	public LogoutResultDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param result
	 */
	public LogoutResultDTO(boolean result) {
		super();
		this.result = result;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
}
