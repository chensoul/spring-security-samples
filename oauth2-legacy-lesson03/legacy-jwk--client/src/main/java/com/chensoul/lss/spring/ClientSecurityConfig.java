package com.chensoul.lss.spring;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

@EnableOAuth2Sso
@EnableWebSecurity
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ClientSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().antMatchers("/").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().disable()
			.logout(logoutCustomizer -> logoutCustomizer.logoutSuccessUrl("/"))
		;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder().build();
	}
}
