package com.chensoul.lss.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {

	@GetMapping("/users/userinfo")
	public Principal getUserInfo(Principal principal) {
		return principal;
	}

}
