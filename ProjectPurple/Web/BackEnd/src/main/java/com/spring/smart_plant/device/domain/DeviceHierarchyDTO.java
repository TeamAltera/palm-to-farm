package com.spring.smart_plant.device.domain;

import java.util.List;

public class DeviceHierarchyDTO {
	private APInfoDTO apInfo;
	private List<SmartFarmInfoDTO> devices;
	/**
	 * 
	 */
	public DeviceHierarchyDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param apInfo
	 * @param devices
	 */
	public DeviceHierarchyDTO(APInfoDTO apInfo, List<SmartFarmInfoDTO> devices) {
		super();
		this.apInfo = apInfo;
		this.devices = devices;
	}

	public APInfoDTO getApInfo() {
		return apInfo;
	}
	public void setApInfo(APInfoDTO apInfo) {
		this.apInfo = apInfo;
	}
	public List<SmartFarmInfoDTO> getDevices() {
		return devices;
	}
	public void setDevices(List<SmartFarmInfoDTO> devices) {
		this.devices = devices;
	}
}
