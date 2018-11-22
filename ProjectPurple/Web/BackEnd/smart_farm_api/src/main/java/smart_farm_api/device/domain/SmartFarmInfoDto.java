package smart_farm_api.device.domain;

import java.sql.Timestamp;

public class SmartFarmInfoDto {
	private int sfCode;
	private int sfPortCnt;
	private int floorCnt;
	private int coolerCnt;
	private char ledCtrlMode;
	private char coolerSt;
	private char ledSt;
	private char pumpSt;
	private int apCode;
	private String innerIp;
	private Timestamp sfRegDate;
	
	/**
	 * @param sfCode
	 * @param sfPortCnt
	 * @param floorCnt
	 * @param userCode
	 * @param coolerCnt
	 * @param ledCtrlMode
	 * @param coolerSt
	 * @param ledSt
	 * @param pumpSt
	 * @param apCode
	 * @param innerIp
	 */
	public SmartFarmInfoDto(int sfCode, int sfPortCnt, int floorCnt, int coolerCnt, char ledCtrlMode,
			char coolerSt, char ledSt, char pumpSt, int apCode, String innerIp) {
		super();
		this.sfCode = sfCode;
		this.sfPortCnt = sfPortCnt;
		this.floorCnt = floorCnt;
		this.coolerCnt = coolerCnt;
		this.ledCtrlMode = ledCtrlMode;
		this.coolerSt = coolerSt;
		this.ledSt = ledSt;
		this.pumpSt = pumpSt;
		this.apCode = apCode;
		this.innerIp = innerIp;
	}

	/**
	 * 
	 */
	public SmartFarmInfoDto() {
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
	public char getCoolerSt() {
		return coolerSt;
	}
	public void setCoolerSt(char coolerSt) {
		this.coolerSt = coolerSt;
	}
	public char getLedSt() {
		return ledSt;
	}
	public void setLedSt(char ledSt) {
		this.ledSt = ledSt;
	}
	public char getPumpSt() {
		return pumpSt;
	}
	public void setPumpSt(char pumpSt) {
		this.pumpSt = pumpSt;
	}
	public int getApCode() {
		return apCode;
	}
	public void setApCode(int apCode) {
		this.apCode = apCode;
	}
	public int getApPublicIp() {
		return apCode;
	}
	public void setApPublicIp(int apPublicIp) {
		this.apCode = apPublicIp;
	}
	public String getInnerIp() {
		return innerIp;
	}
	public void setInnerIp(String innerIp) {
		this.innerIp = innerIp;
	}
	public Timestamp getSfRegDate() {
		return sfRegDate;
	}
	public void setSfRegDate(Timestamp sfRegDate) {
		this.sfRegDate = sfRegDate;
	}
	
}
