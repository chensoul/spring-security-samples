package com.chensoul.lss.web.controller;

import com.chensoul.lss.persistence.UserRepository;
import com.chensoul.lss.service.UserService;
import com.chensoul.lss.web.validation.UsernameExistsException;
import com.chensoul.lss.web.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
