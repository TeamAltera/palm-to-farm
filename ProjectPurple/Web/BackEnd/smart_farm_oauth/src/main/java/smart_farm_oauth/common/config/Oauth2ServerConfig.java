package smart_farm_oauth.common.config;

import java.security.KeyPair;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import lombok.AllArgsConstructor;

@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

	// @Autowired
	// private PasswordEncoder oauthClientPasswordEncoder;

	// private UserDetailsService userDetailsService;

	private AuthenticationManager authenticationManager;

	private ClientDetailsService clientDetailsService;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

		// OAuth2 인증서버 자체의 보안 정보를 설정하는 부분
		endpoints.accessTokenConverter(accessTokenConverter())
				// .userDetailsService(userDetailsService)
				.tokenEnhancer(tokenEnhancerChain)
				.authenticationManager(authenticationManager);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// client에 대한 정보를 설정하는 부분
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		// .passwordEncoder(oauthClientPasswordEncoder);
		// 토큰 확인 경로(/oauth/check_token)을 활성화 시킨다
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// OAuth2서버가 작동하기 위한 Endpoint에 대한 정보를 설정하는 부분
		/*
		 * clients.inMemory() .withClient("foo") .secret("bar")
		 * .authorizedGrantTypes("authorization_code", "referesh_token", "password")
		 * //.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
		 * .resourceIds(RESOURCE_ID) .scopes("read","write")
		 * .accessTokenValiditySeconds(120).//Access token is only valid for 2 minutes.
		 * refreshTokenValiditySeconds(600);//Refresh token is only valid for 10
		 * minutes.
		 */

		clients.withClientDetails(clientDetailsService);
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

		// 공개키와 개인키
		// alias: mytestkey
		// storepass: storeCreeper
		// keypass: changeCreeper
		KeyPair pair = new KeyStoreKeyFactory(new ClassPathResource("server.jks"), "storeCreeper".toCharArray())
				.getKeyPair("mytestkey", "changeCreeper".toCharArray());
		converter.setKeyPair(pair);
		return converter;
	}

	@Bean
	DefaultTokenServices tokenService() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true); // 리프레시 토큰 포함
		return defaultTokenServices;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	@Primary
	public JdbcClientDetailsService jdbcClientDetailsService(DataSource dataSource) {
		// 클라이언트 정보를 DB에서 가져와 사용, OauthClientDetails 스키마 형태를 사용해서 클라이언트 정보를 관리.
		return new JdbcClientDetailsService(dataSource);
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}
}