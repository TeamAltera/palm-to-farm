package smart_farm_api.common.rabbitmq;

import java.sql.Timestamp;
import java.util.HashMap;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import smart_farm_api.common.config.RabbitMQConfig;
import smart_farm_api.sensor.service.InsertSensorServiceImpl;

@Component
@AllArgsConstructor
public class RabbitMessageListenerImpl {

	private InsertSensorServiceImpl insertSensorService;

	private SimpMessagingTemplate messagingTemplate;

	//consumer역할 수행하는 메서드
	@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
	public void onMessage(HashMap<String, Object> income) {

		System.out.println(income.toString());

		// 인터페이스에 센싱 데이터 전송
		messagingTemplate.convertAndSend("/topic/messages" + income.get("ap") + income.get("sta"), income);

		// Timestamp값 변환 (unix to string)
		Timestamp ts = new Timestamp((int) income.get("d") * 1000L);
		income.put("d", ts);

		// 센싱 데이터 저장
		insertSensorService.execute(income);
	}
}
