package smart_farm_api.device.domain;

import java.util.List;

import smart_farm_api.plant.domain.PortStatusDto;

public class CommandDataDto {
	private int sfPort;
	private int plant;
	private List<List<PortStatusDto>> farm;
	/**
	 * 
	 */
	public CommandDataDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param sfPort
	 * @param plant
	 * @param list
	 */
	public CommandDataDto(int sfPort, int plant, List<List<PortStatusDto>> farm) {
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

	public List<List<PortStatusDto>> getFarm() {
		return farm;
	}

	public void setFarm(List<List<PortStatusDto>> list) {
		this.farm = list;
	}
	
}
