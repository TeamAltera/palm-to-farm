package com.spring.smart_plant.DTO.JSON;

public class RabbitMQCustomMessage {
	private int id;
	private int temp;
	
	/**
	 * 기본 생성자는 JSON으로 역직렬화 하기 위해 필요
	 */
	public RabbitMQCustomMessage() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param id
	 * @param temp
	 */
	public RabbitMQCustomMessage(int id, int temp) {
		super();
		this.id = id;
		this.temp = temp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	
	@Override
    public String toString() {
        return "RabbitMQCustomMessage{id="+id+",temp="+temp+"}";
    }
}
