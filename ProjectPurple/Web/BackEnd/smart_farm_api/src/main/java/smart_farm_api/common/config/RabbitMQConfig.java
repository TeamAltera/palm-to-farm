package smart_farm_api.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
	
	//큐 이름
    public static final String QUEUE_NAME = "sensor";
    
    //from
    private static final String EXCHANGE = "amq.direct";
    
    //라우팅 키
    @Value("${rabbit.direct-routing-key}")
    private String directRoutingKey;
    
    @Value("${rabbit.port}")
    private int port;
    
    @Value("${rabbit.host}")
    private String host;
    
    @Value("${rabbit.user}")
    private String user;
    
    @Value("${rabbit.password}")
    private String password;
	
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setRoutingKey(directRoutingKey);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
    
    @Bean
    public SimpleMessageListenerContainer container() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(QUEUE_NAME);
        container.setMessageConverter(jsonMessageConverter());
        return container;
    }
    
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }
    
    @Bean
    public Exchange exchange() {
    	return ExchangeBuilder.directExchange(EXCHANGE).build();
    }
    
    //큐, exchange 바인딩
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(directRoutingKey);
    }
    
    //json converter
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    //커넥션 팩토리 생성 관련
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setUsername(user);
        factory.setPassword(password);
        factory.setPort(port);
        return factory;
    }
}
