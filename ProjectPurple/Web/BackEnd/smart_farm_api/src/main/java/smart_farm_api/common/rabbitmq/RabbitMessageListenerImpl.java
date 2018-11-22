package smart_farm_api.common.rabbitmq;

import java.sql.Timestamp;
import java.util.HashMap;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import smart_farm_api.common.config.RabbitMQConfig;
import smart_farm_api.sensor.service.InsertSensorServiceImpl;

@Component
@AllArgsConstructor
public class RabbitMessageListenerImpl{

	//private MessageSendingOperations<String> messagingTemplate;
	/*private RabbitMessagingTemplate rabbitTemplate;*/
	
	private InsertSensorServiceImpl insertSensorService;
	
	/**
	 * <pre>
	 * -RabbitMQ consumer로 등록된 메서드
	 * </pre>
	 * @param income
	 */
	@RabbitListener(queues=RabbitMQConfig.QUEUE_NAME)
	public void onMessage(HashMap<String, Object> income) {
		// TODO Auto-generated method stub
		/*rabbitTemplate.convertAndSend("/topic/messages"+income.get("id"),income);*/
		
		try {
			System.out.println(income.toString());
			//this.messagingTemplate.convertAndSend("/topic/messages"+income.get("ap")+income.get("sf"),income);
			
			//unix to string
			Timestamp ts=new Timestamp((int)income.get("d") * 1000L);
			income.put("d", ts);
			//insertSensorService.execute(income);//센싱 데이터 저장
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * 1535456522
		 * 1535456522000
		 * 1535456522
		 * t: temp
		 * wt: water temp
		 * wl: water lim
		 * e: elum, 정수 데이터
		 * h: humi
		 * */
		
//		Double.parseDouble((String) income.get("t"));
//		Double.parseDouble((String) income.get("h"));
//		Double.parseDouble((String) income.get("ec"));
//		Double.parseDouble((String) income.get("ph"));
//		Double.parseDouble((String) income.get("wt"));
//		Integer.parseInt((String) income.get("wl"));
//		Integer.parseInt((String) income.get("e"));
	}
}
