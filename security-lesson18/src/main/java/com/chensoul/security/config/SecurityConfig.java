package com.chensoul.security.config;

import com.chensoul.security.persistence.UserRepository;
import com.chensoul.security.security.CustomAuthenticationProvider;
import com.chensoul.security.security.CustomWebAuthenticationDetailsSource;
import com.twilio.sdk.TwilioRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
  private final UserRepository userRepository;

  @Value("${twilio.sid}")
  private String accountSid;

  @Value("${twilio.token}")
  private String authToken;


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

  @Configuration
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public static class BasicSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.antMatcher("/code*").authorizeRequests().anyRequest().hasRole("TEMP_USER").and().httpBasic();
    }
  }

  @Bean
  public TwilioRestClient twilioRestClient() {
    return new TwilioRestClient(accountSid, authToken);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
