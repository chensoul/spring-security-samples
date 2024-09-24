package com.chensoul.lss.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
		auth.authenticationProvider(runAsAuthenticationProvider());
		auth.authenticationProvider(daoAuthenticationProvider());
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

		.and()
		.csrf().disable();
	} // @formatter:on

	@Bean
	public AuthenticationProvider runAsAuthenticationProvider() {
		final RunAsImplAuthenticationProvider authProvider = new RunAsImplAuthenticationProvider();
		authProvider.setKey("MyRunAsKey");
		return authProvider;
	}

	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
