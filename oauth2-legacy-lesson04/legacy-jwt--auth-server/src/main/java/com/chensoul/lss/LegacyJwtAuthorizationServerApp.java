package com.chensoul.lss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
public class LegacyJwtAuthorizationServerApp {

    public static void main(String[] args) {
        SpringApplication.run(LegacyJwtAuthorizationServerApp.class, args);
    }

}