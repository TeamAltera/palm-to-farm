package com.spring.smart_plant.services.websocket;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;

public class WebSocketErrorRecoverer extends RejectAndDontRequeueRecoverer{

	@Override
	public void recover(Message message, Throwable cause) {
		// TODO Auto-generated method stub
		System.out.println("rabbit error...");
		System.out.println(new String(message.getBody()));
		System.out.println(cause.toString());
	}

}
