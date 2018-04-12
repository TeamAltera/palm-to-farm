package com.spring.smart_plant.DTO;

public class APInfoDTO {
	private String apPublicIp;
	private String apSsid;

	/**
	 * 
	 */
	public APInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param apPublicIp
	 * @param apSsid
	 */
	public APInfoDTO(String apPublicIp, String apSsid) {
		super();
		this.apPublicIp = apPublicIp;
		this.apSsid = apSsid;
	}

	public String getApPublicIp() {
		return apPublicIp;
	}
	public void setApPublicIp(String apPublicIp) {
		this.apPublicIp = apPublicIp;
	}
	public String getApSsid() {
		return apSsid;
	}
	public void setApSsid(String apSsid) {
		this.apSsid = apSsid;
	}
	
}
