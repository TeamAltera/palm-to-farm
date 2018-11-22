package smart_farm_api.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	//생성자를 통한 인젝션
	//private LoginSuccessHandler loginSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		super.configure(http);
		http
		.csrf()
		.and()
        .httpBasic().disable()
        .headers().frameOptions().sameOrigin().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests().antMatchers("/info").permitAll() //웹소켓 연결을 맺기 위한 최초 핸드셰이크 허용 
        .anyRequest().denyAll();
	}

	//스프링 시큐리티 보안 설정에 적용받지 않을 URL을 등록
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		//스프링 시큐리티가 적용되지 않을 경로 입력, 입력되지 않은 경로라면 로그인 페이지로 302 redirect
		web
			.ignoring()
			.antMatchers("/resources/**")
			.antMatchers("/user/**")
			.antMatchers("/device/**")
			.antMatchers("/log/**")
			.antMatchers("/plant/**")
			.antMatchers("/sensor/**")
			.antMatchers("/device_data/**")
			.antMatchers("/sensor_data/**");
	}
}
