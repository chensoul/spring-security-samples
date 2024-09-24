package com.chensoul.lss.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class LssSecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsService;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
		 auth.userDetailsService(userDetailsService);
    } // @formatter:on

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

		.and().sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry()).and().sessionFixation().none()

		.and()
		.csrf().disable();
	} // @formatter:on

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public static ServletListenerRegistrationBean httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
