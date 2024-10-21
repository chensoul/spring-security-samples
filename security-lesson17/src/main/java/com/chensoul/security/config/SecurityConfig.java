package com.chensoul.security.config;

import com.chensoul.security.security.CustomAuthenticationProvider;
import com.chensoul.security.security.CustomWebAuthenticationDetailsSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final CustomWebAuthenticationDetailsSource authenticationDetailsSource;
  private final CustomAuthenticationProvider customAuthenticationProvider;

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
		auth.authenticationProvider(customAuthenticationProvider);
    } // @formatter:on

  @Override
  protected void configure(HttpSecurity http) throws Exception { // @formatter:off
		http
			.authorizeRequests()
			.antMatchers("/signup", "/user/register","/code*","/isUsing2FA*").permitAll()
			.anyRequest().authenticated()

		.and()
        .formLogin().
            loginPage("/login").permitAll().
            loginProcessingUrl("/doLogin")
			.authenticationDetailsSource(authenticationDetailsSource)


		.and()
		.logout().permitAll().logoutUrl("/logout")

		.and()
		.csrf().disable();
	} // @formatter:on

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
