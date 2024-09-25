package com.chensoul.lss.spring;

import com.nimbusds.jose.jwk.JWKSet;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FrameworkEndpoint
public class JwkSetEndpoint {

  @Autowired
  private JWKSet jwkSet;

  @GetMapping("/endpoint/jwks.json")
  @ResponseBody
  public Map<String, Object> jwks() {
    return this.jwkSet.toJSONObject();
  }
}
