package com.chensoul.security.web.controller;

import com.chensoul.security.persistence.UserRepository;
import com.chensoul.security.service.UserService;
import com.chensoul.security.web.model.User;
import com.chensoul.security.web.validation.UsernameExistsException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
class UserController {
  private final UserRepository userRepository;
  private final UserService userService;

  @GetMapping
  public Iterable<User> list() {
    return this.userRepository.findAll();
  }

  @GetMapping("/{id}")
  public User view(@PathVariable("id") User user) {
    return user;
  }

  @PostMapping
  public User save(@Valid User user) throws UsernameExistsException {
    if (user.getId() == null) {
      userService.createUser(user);
    } else {
      userService.updateUser(user);
    }
    return user;
  }

  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable("id") Long id) {
    this.userRepository.findById(id).ifPresent(user -> this.userRepository.delete(user));
  }

}
