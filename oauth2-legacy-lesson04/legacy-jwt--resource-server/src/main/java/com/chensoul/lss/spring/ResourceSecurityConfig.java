package com.chensoul.lss.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceSecurityConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatchers()
			.antMatchers("/api/projects")
			.and()
			.authorizeRequests().antMatchers(HttpMethod.GET, "/api/projects/**").hasAuthority("ROLE_USER")
			.antMatchers(HttpMethod.POST, "/api/projects").hasAuthority("ROLE_USER")
			.anyRequest().authenticated()
		;
	}

}
