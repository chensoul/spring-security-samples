package com.chensoul.security;

import java.util.HashMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.Map;

@SpringBootApplication
public class PasswordsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PasswordsApplication.class, args);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(ae -> ae.anyRequest().authenticated())
            .formLogin(Customizer.withDefaults())
            .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    UserDetailsPasswordService userDetailsPasswordService(UserDetailsManager userDetailsManager) {
        return (user, newPassword) -> {
            UserDetails updated = User.withUserDetails(user).password(newPassword).build();
            userDetailsManager.updateUser(updated);
            return updated;
        };
    }


    @Bean
    JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

}

@Controller
@ResponseBody
class SecuredController {

    private final UserDetailsManager inMemoryUserDetailsManager;

    SecuredController(UserDetailsManager inMemoryUserDetailsManager) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    @GetMapping("/")
    Map<String, String> hello(Principal principal) {

        UserDetails user = this.inMemoryUserDetailsManager.loadUserByUsername(principal.getName());
        System.out.println(user.getPassword());

        Map map = new HashMap<>();
        map.put("message", "Hello," + principal.getName());

        return map;
    }
}
