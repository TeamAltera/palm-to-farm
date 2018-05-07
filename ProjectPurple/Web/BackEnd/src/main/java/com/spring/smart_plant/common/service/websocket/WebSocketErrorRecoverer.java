package com.spring.smart_plant.common.service.websocket;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;

import com.spring.smart_plant.common.service.rabbitmq.RabbitMQCustomMessage;

public class WebSocketErrorRecoverer extends RejectAndDontRequeueRecoverer{

	@Override
	public void recover(Message message, Throwable cause) {
		// TODO Auto-generated method stub
		//데이터 처리 시 문제가 발생할 경우 rabbit Error라는 문구 출력
		System.out.println(new String(message.getBody()));
		System.out.println(cause.toString());
	}

}
