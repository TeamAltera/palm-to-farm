package com.spring.smart_plant.device.domain;

public class CommandDataDTO {
	private int sfPort;
	private int plant;
	/**
	 * 
	 */
	public CommandDataDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param sfPort
	 * @param plant
	 */
	public CommandDataDTO(int sfPort, int plant) {
		super();
		this.sfPort = sfPort;
		this.plant = plant;
	}
	public int getSfPort() {
		return sfPort;
	}
	public void setSfPort(int sfPort) {
		this.sfPort = sfPort;
	}
	public int getPlant() {
		return plant;
	}
	public void setPlant(int plant) {
		this.plant = plant;
	}
}
