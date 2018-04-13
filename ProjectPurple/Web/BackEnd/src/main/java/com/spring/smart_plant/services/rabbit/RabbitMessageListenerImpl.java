package com.spring.smart_plant.services.rabbit;

import java.util.HashMap;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Component;

import com.spring.smart_plant.DTO.JSON.Request.RabbitMQCustomMessage;

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

	public void listen(Message msg) throws Exception{
		System.out.println("income");
		/*System.out.println(income);*/
		//this.messagingTemplate.convertAndSend("/topic/messages",income);
		
	}

	public void onMessage(HashMap<String, Object> income) {
		// TODO Auto-generated method stub
		System.out.println("income data");
		this.messagingTemplate.convertAndSend("/topic/messages."+income.get("id"),income.get("temp"));
	}
}
