package com.chensoul.lss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class LssSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.
			inMemoryAuthentication().passwordEncoder(passwordEncoder()).
			withUser("user").password(passwordEncoder().encode("pass")).
			roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception { // @formatter:off
		http
			.authorizeRequests()
			.anyRequest().authenticated()

			.and()
			.formLogin().
			loginPage("/login").permitAll().
			loginProcessingUrl("/doLogin")

			.and()
			.logout().permitAll().logoutUrl("/logout")

			.and()
			.csrf().disable()
		;
	} // @formatter:on

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
