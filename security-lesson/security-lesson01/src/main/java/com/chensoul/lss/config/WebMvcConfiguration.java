package com.chensoul.lss.config;

import com.chensoul.lss.service.UserService;
import com.chensoul.lss.web.model.User;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
  private final UserService userService;

  @PostConstruct
  private void saveTestUser() {
    User user = new User();
    user.setUsername("user");
    user.setPassword("pass");
    user.setPasswordConfirmation("pass");
    userService.createUser(user);

    user = new User();
    user.setUsername("admin");
    user.setPassword("pass");
    user.setPasswordConfirmation("pass");
    userService.createUser(user);
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new Converter<String, User>() {
      @Override
      public User convert(String id) {
        return userService.findById(Long.valueOf(id));
      }
    });
  }

  @Bean
  public Converter<String, User> messageConverter() {
    return new Converter<String, User>() {
      @Override
      public User convert(String id) {
        return userService.findById(Long.valueOf(id));
      }
    };
  }
}
