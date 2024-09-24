package com.chensoul.lss.spring;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.Map;

@Configuration
@EnableResourceServer
@EnableConfigurationProperties(AuthorizationServerProperties.class)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	private static final String JWK_KID = RandomStringUtils.randomAlphanumeric(6);

	@Autowired
	private AuthorizationServerProperties properties;
	@Autowired
	private ApplicationContext context;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.requestMatchers()
			.antMatchers("/users/userinfo")
			.and()
			.authorizeRequests()
			.antMatchers("/users/userinfo")
			.authenticated();
	}

	@Bean
	public KeyPair keyPair() {
		Resource keyStore = this.context.getResource(properties.getJwt().getKeyStore());
		KeyStoreKeyFactory ksFactory = new KeyStoreKeyFactory(keyStore, properties.getJwt().getKeyStorePassword().toCharArray());

		if (StringUtils.isNotBlank(properties.getJwt().getKeyPassword())) {
			return ksFactory.getKeyPair(properties.getJwt().getKeyAlias(), properties.getJwt().getKeyPassword().toCharArray());
		}
		return ksFactory.getKeyPair(properties.getJwt().getKeyAlias());
	}

	@Bean
	public JWKSet jwkSet() {
		RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic());
		builder.keyUse(KeyUse.SIGNATURE).algorithm(JWSAlgorithm.RS256).keyID(JWK_KID);
		return new JWKSet(builder.build());
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		Map<String, String> customHeaders = Collections.singletonMap("kid", JWK_KID);
		return new JwkKeyPairAccessTokenConverter(customHeaders, keyPair());
	}

}
