package com.chensoul.security.web.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured")
class SecuredController {

  @PreAuthorize("isAdmin()")
  @GetMapping("/isAdmin")
  public String isAdmin() {
    return "isAdmin";
  }

  @PreAuthorize("hasAuthority('USER_READ')")
  @GetMapping("/read")
  public String read() {
    return "USER_READ";
  }

  @Secured("ROLE_USER")
  @GetMapping("/role")
  public String role() {
    return "ROLE_USER";
  }

}
