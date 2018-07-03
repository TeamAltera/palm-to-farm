package com.spring.smart_plant.common.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurerAdapter {
	// static 리소스 처리
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");

		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				/*.apis(RequestHandlerSelectors.basePackage("com.spring.smart_plant.device.controller"))
				.apis(RequestHandlerSelectors.basePackage("com.spring.smart_plant.log.controller"))
				.apis(RequestHandlerSelectors.basePackage("com.spring.smart_plant.mail.controller"))
				.apis(RequestHandlerSelectors.basePackage("com.spring.smart_plant.sensor.controller"))
				.apis(RequestHandlerSelectors.basePackage("com.spring.smart_plant.user.controller"))*/
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();

	}
}
