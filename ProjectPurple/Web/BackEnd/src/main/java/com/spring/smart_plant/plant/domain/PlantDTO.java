package com.spring.smart_plant.plant.domain;

import java.sql.Timestamp;

public class PlantDTO {
	private String plantName;
	private double optTemp;
	private double maxTemp;
	private double minTemp;
	private double minPh;
	private double maxPh;
	private double maxEc;
	private double minEc;
	private Timestamp farmingDate;
	private int plantCode;
	/**
	 * 
	 */
	public PlantDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param plantName
	 * @param optTemp
	 * @param maxTemp
	 * @param minTemp
	 * @param minPh
	 * @param maxPh
	 * @param maxEc
	 * @param minEc
	 * @param farmingDate
	 * @param plantCode
	 */
	public PlantDTO(String plantName, double optTemp, double maxTemp, double minTemp, double minPh, double maxPh,
			double maxEc, double minEc, Timestamp farmingDate, int plantCode) {
		super();
		this.plantName = plantName;
		this.optTemp = optTemp;
		this.maxTemp = maxTemp;
		this.minTemp = minTemp;
		this.minPh = minPh;
		this.maxPh = maxPh;
		this.maxEc = maxEc;
		this.minEc = minEc;
		this.farmingDate = farmingDate;
		this.plantCode = plantCode;
	}
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public double getOptTemp() {
		return optTemp;
	}
	public void setOptTemp(double optTemp) {
		this.optTemp = optTemp;
	}
	public double getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;
	}
	public double getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(double minTemp) {
		this.minTemp = minTemp;
	}
	public double getMinPh() {
		return minPh;
	}
	public void setMinPh(double minPh) {
		this.minPh = minPh;
	}
	public double getMaxPh() {
		return maxPh;
	}
	public void setMaxPh(double maxPh) {
		this.maxPh = maxPh;
	}
	public double getMaxEc() {
		return maxEc;
	}
	public void setMaxEc(double maxEc) {
		this.maxEc = maxEc;
	}
	public double getMinEc() {
		return minEc;
	}
	public void setMinEc(double minEc) {
		this.minEc = minEc;
	}
	public Timestamp getFarmingDate() {
		return farmingDate;
	}
	public void setFarmingDate(Timestamp farmingDate) {
		this.farmingDate = farmingDate;
	}
	public int getPlantCode() {
		return plantCode;
	}
	public void setPlantCode(int plantCode) {
		this.plantCode = plantCode;
	}
	
}
