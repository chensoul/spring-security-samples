package com.chensoul.lss.spring;

import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

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
