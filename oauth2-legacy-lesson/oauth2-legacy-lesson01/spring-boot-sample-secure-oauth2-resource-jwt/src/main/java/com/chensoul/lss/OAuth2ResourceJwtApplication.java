/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chensoul.lss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Use <a target="_blank" href=
 * "https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#oauth2resourceserver">Spring
 * Security's built-in support</a>
 */
@SpringBootApplication
@EnableResourceServer
public class OAuth2ResourceJwtApplication extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // @formatter:off
		http.antMatcher("/secured/**").authorizeRequests().anyRequest().authenticated();
		// @formatter:on
  }

  public static void main(String[] args) {
    SpringApplication.run(OAuth2ResourceJwtApplication.class, args);
  }

}
