package smart_farm_api.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
	
	@Bean
	public Docket api() {
		//api문서화 시킬 패키지 설정
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                /*.apis(RequestHandlerSelectors.basePackage("smart_farm_api.log"))
                .apis(RequestHandlerSelectors.basePackage("smart_farm_api.plant"))
                .apis(RequestHandlerSelectors.basePackage("smart_farm_api.sensor"))
                .apis(RequestHandlerSelectors.basePackage("smart_farm_api.user"))*/
                .paths(PathSelectors.any())
                .build();
    }
}

