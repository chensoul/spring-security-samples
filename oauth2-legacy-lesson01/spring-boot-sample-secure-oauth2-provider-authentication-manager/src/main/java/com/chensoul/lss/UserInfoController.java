package com.chensoul.lss;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class UserInfoController {

    @GetMapping("/users/userinfo")
    public Map<String, Object> getUserInfo(OAuth2Authentication authentication) {
        return Collections.singletonMap("preferred_username", authentication.getName());
    }

}
