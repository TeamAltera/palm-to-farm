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
 *		ec:
 *		ph:
	}
*/
public class SensorDataDTO {
	private Timestamp d;
//	private int sfCode;
//	private int apCode;
	private double t;
	private double h;
	private int e;
	private double wt;
	private double wl;
	private double ec;
	private double ph;
	/**
	 * @param d
	 * @param t
	 * @param h
	 * @param e
	 * @param wt
	 * @param wl
	 * @param ec
	 * @param ph
	 */
	public SensorDataDTO(Timestamp d, double t, double h, int e, double wt, double wl, double ec, double ph) {
		super();
		this.d = d;
		this.t = t;
		this.h = h;
		this.e = e;
		this.wt = wt;
		this.wl = wl;
		this.ec = ec;
		this.ph = ph;
	}
	/**
	 * 
	 */
	public SensorDataDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Timestamp getD() {
		return d;
	}
	public void setD(Timestamp d) {
		this.d = d;
	}
	public double getT() {
		return t;
	}
	public void setT(double t) {
		this.t = t;
	}
	public double getH() {
		return h;
	}
	public void setH(double h) {
		this.h = h;
	}
	public int getE() {
		return e;
	}
	public void setE(int e) {
		this.e = e;
	}
	public double getWt() {
		return wt;
	}
	public void setWt(double wt) {
		this.wt = wt;
	}
	public double getWl() {
		return wl;
	}
	public void setWl(double wl) {
		this.wl = wl;
	}
	public double getEc() {
		return ec;
	}
	public void setEc(double ec) {
		this.ec = ec;
	}
	public double getPh() {
		return ph;
	}
	public void setPh(double ph) {
		this.ph = ph;
	}
	
	
}
