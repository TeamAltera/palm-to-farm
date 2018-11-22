package smart_farm_api.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	@Value("${farm.token-endpoint-url}")
	private String tokenEndPointUrl;
	
	@Value("${farm.client-id}")
	private String clientId;
	
	@Value("${farm.client-secret}")
	private String clientSecret;
	
	@Value("${farm.security.oauth.resource.jwt.key-value}")
	private String publicKey;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//리소스 서버의 uri경로를 OAuth2 인증받게 만들도록 설정하는 부분
		http
			//.addFilterBefore(filter, beforeFilter)
			.cors()
				.and()
			.csrf()
				.disable()
			.headers()
				.frameOptions()
				.disable() //헤더 충돌 방지
				.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //사용자의 쿠키에 세션을 저장하지 않겠다
				.and()
			.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS,"/**").permitAll() //OPTIONS preflight요청 시 오류를 리턴하지 않도록
				.antMatchers("/device/add/auto").permitAll()
				.antMatchers("/info").permitAll()
				.antMatchers(HttpMethod.GET, "user/**").authenticated()
				//.antMatchers(HttpMethod.POST, "/**").authenticated()
				.anyRequest().permitAll(); 
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// TODO Auto-generated method stub
		resources.tokenServices(tokenService());
		resources.resourceId("member");
	}
	
	@Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
    
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {		
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		//.jks에 대한 publicKey포함
		converter.setVerifierKey(publicKey);
		return converter;
	}
	
	@Bean
	@Primary
	public DefaultTokenServices tokenService() {
		/*RemoteTokenServices tokenService = new RemoteTokenServices();
		//resource서버는 요청 수행이전에 해당 URL로 token이 유효한지 체크
		tokenService.setCheckTokenEndpointUrl(tokenEndPointUrl);
		//client-id, client-secret설정
		tokenService.setClientId(clientId);
		tokenService.setClientSecret(clientSecret);*/
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}
}
