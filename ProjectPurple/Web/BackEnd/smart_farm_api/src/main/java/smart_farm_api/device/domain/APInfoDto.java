package smart_farm_api.device.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class APInfoDto {
	private int apCode;
	private String apPublicIp;
	private String apSsid;
	private int userCode;
	private int apSfCnt;
	private Timestamp apRegDate;
	private List<SmartFarmInfoDto> plantDevices;

	/**
	 * 
	 */
	public APInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param apCode
	 * @param apPublicIp
	 * @param apSsid
	 * @param userCode
	 * @param apSfCnt
	 * @param apRegDate
	 * @param plantDevices
	 */
	public APInfoDto(int apCode, String apPublicIp, String apSsid, int userCode, int apSfCnt, Timestamp apRegDate,
			List<SmartFarmInfoDto> plantDevices) {
		super();
		this.apCode = apCode;
		this.apPublicIp = apPublicIp;
		this.apSsid = apSsid;
		this.userCode = userCode;
		this.apSfCnt = apSfCnt;
		this.apRegDate = apRegDate;
		this.plantDevices = plantDevices;
	}
	
	public APInfoDto(String apPublicIp, String apSsid, int userCode, int apSfCnt) {
		super();
		this.apPublicIp = apPublicIp;
		this.apSsid = apSsid;
		this.userCode = userCode;
		this.apSfCnt = apSfCnt;
	}

	public int getApCode() {
		return apCode;
	}

	public void setApCode(int apCode) {
		this.apCode = apCode;
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

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public int getApSfCnt() {
		return apSfCnt;
	}

	public void setApSfCnt(int apSfCnt) {
		this.apSfCnt = apSfCnt;
	}

	public Timestamp getApRegDate() {
		return apRegDate;
	}

	public void setApRegDate(Timestamp apRegDate) {
		this.apRegDate = apRegDate;
	}

	public List<SmartFarmInfoDto> getPlantDevices() {
		return plantDevices;
	}

	public void setPlantDevices(List<SmartFarmInfoDto> plantDevices) {
		this.plantDevices = plantDevices;
	}	
}
