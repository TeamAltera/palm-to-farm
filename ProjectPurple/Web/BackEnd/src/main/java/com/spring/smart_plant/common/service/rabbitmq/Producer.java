package com.spring.smart_plant.common.service.rabbitmq;

import java.util.Random;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

public class Producer {
	public void send(Object obj) {
		CachingConnectionFactory cf = new CachingConnectionFactory("localhost", 5672);
	    cf.setUsername("guest");
	    cf.setPassword("guest");
	 
	    //메시지 보내기
	    /*RabbitTemplate template = new RabbitTemplate(cf);
	    template.setExchange("amq.direct");
	    template.setQueue("myQueue");
	    template.setMessageConverter(new Jackson2JsonMessageConverter());
	    Integer i=new Random().nextInt(200);
	    template.convertAndSend("foo.bar",new RabbitMQCustomMessage(0, i));*/
	    /*template.convertAndSend("foo.bar", new RabbitMQCustomMessage(0, i));*/
	    /*template.convertAndSend("foo.bar", i.toString());*/
	    cf.destroy();
	}
}
