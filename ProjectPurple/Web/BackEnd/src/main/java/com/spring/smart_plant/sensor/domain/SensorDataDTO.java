package com.spring.smart_plant.sensor.domain;

import java.sql.Timestamp;

/*{
 *		date:
 *		sfCode:
 *		temp:
 *		humi:
 *		elum:
 *		waterTemp:
 *		waterLim: 
	}
*/
public class SensorDataDTO {
	private Timestamp date;
	private int sfCode;
	private int temp;
	private int humi;
	private int elum;
	private int waterTemp;
	private int waterLim;
	
	/**
	 * @param date
	 * @param sfCode
	 * @param temp
	 * @param humi
	 * @param elum
	 * @param waterTemp
	 * @param waterLim
	 */
	public SensorDataDTO(Timestamp date, int sfCode, int temp, int humi, int elum, int waterTemp, int waterLim) {
		super();
		this.date = date;
		this.sfCode = sfCode;
		this.temp = temp;
		this.humi = humi;
		this.elum = elum;
		this.waterTemp = waterTemp;
		this.waterLim = waterLim;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public int getSfCode() {
		return sfCode;
	}
	public void setSfCode(int sfCode) {
		this.sfCode = sfCode;
	}
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	public int getHumi() {
		return humi;
	}
	public void setHumi(int humi) {
		this.humi = humi;
	}
	public int getElum() {
		return elum;
	}
	public void setElum(int elum) {
		this.elum = elum;
	}
	public int getWaterTemp() {
		return waterTemp;
	}
	public void setWaterTemp(int waterTemp) {
		this.waterTemp = waterTemp;
	}
	public int getWaterLim() {
		return waterLim;
	}
	public void setWaterLim(int waterLim) {
		this.waterLim = waterLim;
	}
	
}
