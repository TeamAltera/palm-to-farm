package com.spring.smart_plant.DTO.Response;

public class MemberJoinResultDTO {
	private boolean result;
	
	/**
	 * 
	 */
	public MemberJoinResultDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param result
	 */
	public MemberJoinResultDTO(boolean result) {
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
