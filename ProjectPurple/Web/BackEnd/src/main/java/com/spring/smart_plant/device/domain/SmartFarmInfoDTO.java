package com.spring.smart_plant.device.domain;

public class SmartFarmInfoDTO {
	private int sfCode;
	private int sfPortCnt;
	private int floorCnt;
	private int userCode;
	private int coolerCnt;
	private char ledCtrlMode;
	private String tempDelay;
	private String humiDelay;
	private String elumDelay;
	private String waterTempDelay;
	private String waterLimDelay;
	private String apPublicIp;
	private String innerIp;
	
	/**
	 * @param sfCode
	 * @param sfPortCnt
	 * @param floorCnt
	 * @param userCode
	 * @param coolerCnt
	 * @param ledCtrlMode
	 * @param tempDelay
	 * @param humiDelay
	 * @param elumDelay
	 * @param waterTempDelay
	 * @param waterLimDelay
	 * @param apMac
	 * @param apPrivateIp
	 * @param innerPort
	 * @param innerIp
	 */
	public SmartFarmInfoDTO(int sfCode, int sfPortCnt, int floorCnt, int userCode, int coolerCnt, char ledCtrlMode,
			String tempDelay, String humiDelay, String elumDelay, String waterTempDelay, String waterLimDelay,
			String apPublicIp,  String innerIp) {
		super();
		this.sfCode = sfCode;
		this.sfPortCnt = sfPortCnt;
		this.floorCnt = floorCnt;
		this.userCode = userCode;
		this.coolerCnt = coolerCnt;
		this.ledCtrlMode = ledCtrlMode;
		this.tempDelay = tempDelay;
		this.humiDelay = humiDelay;
		this.elumDelay = elumDelay;
		this.waterTempDelay = waterTempDelay;
		this.waterLimDelay = waterLimDelay;
		this.apPublicIp = apPublicIp;
		this.innerIp = innerIp;
	}
	
	/**
	 * 
	 */
	public SmartFarmInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getSfCode() {
		return sfCode;
	}
	public void setSfCode(int sfCode) {
		this.sfCode = sfCode;
	}
	public int getSfPortCnt() {
		return sfPortCnt;
	}
	public void setSfPortCnt(int sfPortCnt) {
		this.sfPortCnt = sfPortCnt;
	}
	public int getFloorCnt() {
		return floorCnt;
	}
	public void setFloorCnt(int floorCnt) {
		this.floorCnt = floorCnt;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public int getCoolerCnt() {
		return coolerCnt;
	}
	public void setCoolerCnt(int coolerCnt) {
		this.coolerCnt = coolerCnt;
	}
	public char getLedCtrlMode() {
		return ledCtrlMode;
	}
	public void setLedCtrlMode(char ledCtrlMode) {
		this.ledCtrlMode = ledCtrlMode;
	}
	public String getTempDelay() {
		return tempDelay;
	}
	public void setTempDelay(String tempDelay) {
		this.tempDelay = tempDelay;
	}
	public String getHumiDelay() {
		return humiDelay;
	}
	public void setHumiDelay(String humiDelay) {
		this.humiDelay = humiDelay;
	}
	public String getElumDelay() {
		return elumDelay;
	}
	public void setElumDelay(String elumDelay) {
		this.elumDelay = elumDelay;
	}
	public String getWaterTempDelay() {
		return waterTempDelay;
	}
	public void setWaterTempDelay(String waterTempDelay) {
		this.waterTempDelay = waterTempDelay;
	}
	public String getWaterLimDelay() {
		return waterLimDelay;
	}
	public void setWaterLimDelay(String waterLimDelay) {
		this.waterLimDelay = waterLimDelay;
	}
	public String getApPublicIp() {
		return apPublicIp;
	}
	public void setApPublicIp(String apPublicIp) {
		this.apPublicIp = apPublicIp;
	}
	public String getInnerIp() {
		return innerIp;
	}
	public void setInnerIp(String innerIp) {
		this.innerIp = innerIp;
	}
	
}
