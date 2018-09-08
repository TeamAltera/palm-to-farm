package com.spring.smart_plant.device.domain;

import java.util.List;

import com.spring.smart_plant.plant.domain.PortStatusDTO;

public class CommandDataDTO {
	private int sfPort;
	private int plant;
	private List<List<PortStatusDTO>> farm;
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
	 * @param list
	 */
	public CommandDataDTO(int sfPort, int plant, List<List<PortStatusDTO>> farm) {
		super();
		this.sfPort = sfPort;
		this.plant = plant;
		this.farm = farm;
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

	public List<List<PortStatusDTO>> getFarm() {
		return farm;
	}

	public void setFarm(List<List<PortStatusDTO>> list) {
		this.farm = list;
	}
	
}
