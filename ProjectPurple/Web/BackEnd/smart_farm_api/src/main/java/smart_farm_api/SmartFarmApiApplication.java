package smart_farm_api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@MapperScan(basePackages="smart_farm_api")
@PropertySource(value= {
		"classpath:static/properties/oauth2.properties",
		"classpath:static/properties/rabbit.properties",
		"classpath:static/properties/websocket.properties"
},ignoreResourceNotFound=true)
//ignoreResourceNotFound를 통해 없는 파일은 무시
public class SmartFarmApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartFarmApiApplication.class, args);
	}
}
