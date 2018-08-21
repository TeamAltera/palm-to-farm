package com.spring.smart_plant.plant.domain;

public class InfoDTO {
	private Integer apCode;
	private Integer sfCode;
	private Integer plantCode;
	/**
	 * @param apCode
	 * @param sfCode
	 * @param plantCode
	 */
	public InfoDTO(Integer apCode, Integer sfCode, Integer plantCode) {
		super();
		this.apCode = apCode;
		this.sfCode = sfCode;
		this.plantCode = plantCode;
	}
	/**
	 * 
	 */
	public InfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getApCode() {
		return apCode;
	}
	public void setApCode(Integer apCode) {
		this.apCode = apCode;
	}
	public Integer getSfCode() {
		return sfCode;
	}
	public void setSfCode(Integer sfCode) {
		this.sfCode = sfCode;
	}
	public Integer getPlantCode() {
		return plantCode;
	}
	public void setPlantCode(Integer plantCode) {
		this.plantCode = plantCode;
	}	
	
}
