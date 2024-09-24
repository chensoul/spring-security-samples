package com.chensoul.lss.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class LssSecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsService;
	private final DataSource dataSource;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
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
		.rememberMe()
		.key("lssAppKey")
		.tokenValiditySeconds(604800) // 1 week = 604800
		.tokenRepository(persistentTokenRepository())

		.and()
		.logout().permitAll().logoutUrl("/logout")

		.and()
		.csrf().disable();
	} // @formatter:on

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		final JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
