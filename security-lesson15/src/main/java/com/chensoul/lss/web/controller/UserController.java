package com.chensoul.lss.web.controller;

import com.chensoul.lss.security.ActiveUserService;
import com.chensoul.lss.web.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

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
