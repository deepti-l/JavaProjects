package com.dataextraction.security.oauth2.config;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Configuration
@EnableAuthorizationServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(ServerSecurityConfig.class)
public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {

	private static final String RESOURCE_ID = "data-extraction-rest-api";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder oauthClientPasswordEncoder;

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
		// return new JdbcTokenStore(dataSource);
	}

	@Bean
	public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
		return new OAuth2AccessDeniedHandler();
	}

	@RequestMapping("/user")
	@ResponseBody
	public Principal user(Principal user) {
		return user;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
				.passwordEncoder(oauthClientPasswordEncoder);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("spring-security-oauth2-read-write-client")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token").authorities("USER")
				.scopes("read", "write")
//				.resourceIds("resource-server-rest-api")
				.secret("$2a$04$soeOR.QFmClXeFIrhJVLWOQxfHjsJLSpWrU1iGxcMGdu.a5hvfY4W");


//		clients.inMemory().withClient("spring-security-oauth2-read-write-client")
//		.authorizedGrantTypes("password", "authorization_code", "refresh_token")
//		.authorities("ROLE_USER", "ROLE_USER_INSERT", "ROLE_USER_EMPLOYEE").scopes("read", "write")
//		.resourceIds(RESOURCE_ID).secret("$2a$04$soeOR.QFmClXeFIrhJVLWOQxfHjsJLSpWrU1iGxcMGdu.a5hvfY4W")
//		.and()
//		.withClient("spring-security-oauth2-read-client")
//		.authorizedGrantTypes("password", "authorization_code", "refresh_token")
//		.authorities("ROLE_USER", "ROLE_USER_EMPLOYEE").scopes("read").resourceIds(RESOURCE_ID)
//		.secret("$2a$04$WGq2P9egiOYoOFemBRfsiO9qTcyJtNRnPKNBl5tokP7IP.eZn93km");

		// clients.jdbc(dataSource);
		// clients.inMemory().withClient("tonr")
		// .resourceIds(SPARKLR_RESOURCE_ID)
		// .authorizedGrantTypes("authorization_code", "implicit")
		// .authorities("ROLE_CLIENT")
		// .scopes("read", "write")
		// .secret("secret")
		// .and()
		// .withClient("tonr-with-redirect")
		// .resourceIds(SPARKLR_RESOURCE_ID)
		// .authorizedGrantTypes("authorization_code", "implicit")
		// .authorities("ROLE_CLIENT")
		// .scopes("read", "write")
		// .secret("secret")
		// .redirectUris(tonrRedirectUri)
		// .and()
		// .withClient("my-client-with-registered-redirect")
		// .resourceIds(SPARKLR_RESOURCE_ID)
		// .authorizedGrantTypes("authorization_code", "client_credentials")
		// .authorities("ROLE_CLIENT")
		// .scopes("read", "trust")
		// .redirectUris("http://anywhere?key=value")
		// .and()
		// .withClient("my-trusted-client")
		// .authorizedGrantTypes("password", "authorization_code", "refresh_token",
		// "implicit")
		// .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
		// .scopes("read", "write", "trust")
		// .accessTokenValiditySeconds(60)
		// .and()
		// .withClient("my-trusted-client-with-secret")
		// .authorizedGrantTypes("password", "authorization_code", "refresh_token",
		// "implicit")
		// .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
		// .scopes("read", "write", "trust")
		// .secret("somesecret")
		// .and()
		// .withClient("my-less-trusted-client")
		// .authorizedGrantTypes("authorization_code", "implicit")
		// .authorities("ROLE_CLIENT")
		// .scopes("read", "write", "trust")
		// .and()
		// .withClient("my-less-trusted-autoapprove-client")
		// .authorizedGrantTypes("implicit")
		// .authorities("ROLE_CLIENT")
		// .scopes("read", "write", "trust")
		// .autoApprove(true);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService);
	}
}
