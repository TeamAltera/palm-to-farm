package smart_farm_api.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer{

	/*@Value("${websocket.host}")
	private String host;
	
	@Value("${websocket.port}")
	private int port;
	
	@Value("${websocket.client.login}")
	private String clientLogin;
	
	@Value("${websocket.client.passcode}")
	private String clientPasscode;
	
	@Value("${websocket.system.login}")
	private String systemLogin;
	
	@Value("${websocket.system.passcode}")
	private String systemPasscode;*/
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// TODO Auto-generated method stub
		registry.enableSimpleBroker("/topic"); //broker relay 설정
			/*.setRelayHost(host)
			.setRelayPort(port)
			.setClientLogin(clientLogin)
			.setClientPasscode(clientPasscode)
			.setSystemLogin(systemLogin)
			.setSystemPasscode(systemPasscode);*/
		registry.setApplicationDestinationPrefixes("/app");
		//registry.setUserDestinationPrefix("/user").setUserRegistryOrder(40);
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// TODO Auto-generated method stub
		//subscriber가 구독하는 endpoint설정
		registry.addEndpoint("/device_data")
			.setAllowedOrigins("*")
			.withSockJS();
		registry.addEndpoint("/sensing_data")
			.setAllowedOrigins("*")
			.withSockJS();
	}
}
