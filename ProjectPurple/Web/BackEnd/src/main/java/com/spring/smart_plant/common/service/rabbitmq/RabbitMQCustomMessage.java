package com.spring.smart_plant.common.service.rabbitmq;

import org.springframework.amqp.core.Message;

public class RabbitMQCustomMessage{
	private Integer id;
	private Double t;
	private Double wt;
	private Double wl;
	private Integer e;
	private Double h;
	
	/**
	 * 기본 생성자는 JSON으로 역직렬화 하기 위해 필요
	 */
	public RabbitMQCustomMessage() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param id
	 * @param t
	 * @param wt
	 * @param wl
	 * @param e
	 * @param h
	 */
	public RabbitMQCustomMessage(Integer id, Double t, Double wt, Double wl, Integer e, Double h) {
		this.id = id;
		this.t = t;
		this.wt = wt;
		this.wl = wl;
		this.e = e;
		this.h = h;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getT() {
		return t;
	}

	public void setT(Double t) {
		this.t = t;
	}

	public Double getWt() {
		return wt;
	}

	public void setWt(Double wt) {
		this.wt = wt;
	}

	public Double getWl() {
		return wl;
	}

	public void setWl(Double wl) {
		this.wl = wl;
	}

	public Integer getE() {
		return e;
	}

	public void setE(Integer e) {
		this.e = e;
	}

	public Double getH() {
		return h;
	}

	public void setH(Double h) {
		this.h = h;
	}

	@Override
    public String toString() {
        return "RabbitMQCustomMessage{id="+id+",t="+t+",wt="+wt+
        		",e="+e+",h="+h+",wl="+wl+"}";
    }
}
