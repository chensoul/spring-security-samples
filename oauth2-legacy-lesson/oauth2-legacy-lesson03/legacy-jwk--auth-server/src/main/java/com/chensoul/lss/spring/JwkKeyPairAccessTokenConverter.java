package com.chensoul.lss.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.Collections;
import java.util.Map;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 为什么会想到这样实现？
 */
public class JwkKeyPairAccessTokenConverter extends JwtAccessTokenConverter {
  private RsaSigner signer;
  private Map<String, String> headers;
  private ObjectMapper objectMapper = new ObjectMapper();

  public JwkKeyPairAccessTokenConverter(KeyPair keyPair, Map<String, String> headers) {
    super();
    super.setKeyPair(keyPair);
    this.signer = new RsaSigner((RSAPrivateKey) keyPair.getPrivate());
    this.headers = headers;
  }

  public JwkKeyPairAccessTokenConverter(KeyPair keyPair) {
    this(keyPair, Collections.emptyMap());
  }

  @Override
  protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    String content;
    try {
      content = this.objectMapper.writeValueAsString(getAccessTokenConverter().convertAccessToken(accessToken, authentication));
    } catch (Exception ex) {
      throw new IllegalStateException("Cannot convert access token to JSON", ex);
    }
    return JwtHelper.encode(content, this.signer, this.headers).getEncoded();
  }

}
