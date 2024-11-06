package com.chensoul.security.web.controller;

import com.chensoul.security.security.ActiveUserService;
import com.chensoul.security.persistence.User;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping
class UserController {
  private final ActiveUserService activeUserService;

  @GetMapping("/users/active")
  public Iterable<User> list() {
    return activeUserService.getActiveUsers().stream().map(s -> {
      User user = new User();
      user.setUsername(s);
      return user;
    }).collect(Collectors.toList());
  }
}
