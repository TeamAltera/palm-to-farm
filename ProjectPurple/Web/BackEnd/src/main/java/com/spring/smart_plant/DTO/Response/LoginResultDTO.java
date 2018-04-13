package com.spring.smart_plant.DTO.Response;

import java.util.ArrayList;

import com.spring.smart_plant.DTO.SmartFarmInfoDTO;

//로그인 결과 반환 시에 사용되는DTO
public class LoginResultDTO {
	private boolean result;
	private String resultDetail;
	private String token;
	private ArrayList<SmartFarmInfoDTO> machines;
	
	/**
	 * 
	 */
	public LoginResultDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param result
	 */
	public LoginResultDTO(boolean result, String resultDetail) {
		super();
		this.result = result;
		this.resultDetail= resultDetail;
	}
	
	/**
	 * @param result
	 * @param resultDetail
	 * @param sessionLimitTime
	 * @param machines
	 */
	public LoginResultDTO(boolean result, String resultDetail, String token, ArrayList<SmartFarmInfoDTO> machines) {
		super();
		this.result = result;
		this.resultDetail = resultDetail;
		this.token = token;
		this.machines = machines;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getResultDetail() {
		return resultDetail;
	}

	public void setResultDetail(String resultDetail) {
		this.resultDetail = resultDetail;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ArrayList<SmartFarmInfoDTO> getMachines() {
		return machines;
	}

	public void setMachines(ArrayList<SmartFarmInfoDTO> machines) {
		this.machines = machines;
	}
}
