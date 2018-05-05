package com.spring.smart_plant.common.service.rabbitmq;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
public class RabbitMessageListenerImpl{

	private MessageSendingOperations<String> messagingTemplate;
	
	/*@Autowired
	public MessageListenerImpl(final MessageSendingOperations<String> messagingTemplate) {
		this.messagingTemplate=messagingTemplate;
	}*/
	
	@Autowired
	public void setMessagingTemplate(MessageSendingOperations<String> messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	//RabbitMQ consumer로 등록된 메서드
	public void onMessage(HashMap<String, Object> income) {
		// TODO Auto-generated method stub
		System.out.println("income data");
		this.messagingTemplate.convertAndSend("/topic/messages."+income.get("id"),income.get("temp"));
		//STOMP를 사용하여 MQ로 데이터 전달
		/*double temp=(double) income.get("temp");
		double humi=(double) income.get("humi");
		double waterTemp=(double) income.get("waterTemp");
		double waterLim=(double) income.get("waterLim");
		double elum=(double) income.get("elum");
		DAO dao=new DAO();*/
		/*dao.insertData(new SensorDataDTO(date, sfCode, temp, humi, elum, waterTemp, waterLim));*/
	}
}
