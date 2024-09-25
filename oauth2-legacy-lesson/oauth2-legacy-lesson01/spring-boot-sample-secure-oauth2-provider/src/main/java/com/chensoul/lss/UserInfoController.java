package com.chensoul.lss;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

  @GetMapping("/users/userinfo")
  public Principal getUserInfo(Principal principal) {
    return principal;
  }

}
