package com.chensoul.lss;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests confirming behavior of minimally-configured Authorization Server
 *
 * @author Josh Cummings
 */
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class OAuth2ProviderApplicationTests {

  private static final String CLIENT_ID = "client";

  private static final String CLIENT_SECRET = "secret";

  private static final RequestPostProcessor CLIENT_CREDENTIALS = httpBasic(CLIENT_ID, CLIENT_SECRET);

  @Autowired
  MockMvc mvc;

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void tokenWhenUsingClientCredentialsThenIsValid() throws Exception {
    MvcResult result = this.mvc.perform(post("/oauth/token").with(CLIENT_CREDENTIALS)
                    .param("grant_type", "client_credentials").param("scope", "any")).andExpect(status().isOk())
            .andReturn();

    String accessToken = extract(result, "access_token");

    result = this.mvc.perform(post("/oauth/check_token").with(CLIENT_CREDENTIALS).param("token", accessToken))
            .andReturn();

    assertThat(Boolean.valueOf(extract(result, "active"))).isTrue();
  }

  private String extract(MvcResult result, String property) throws Exception {
    return this.objectMapper.readValue(result.getResponse().getContentAsString(), Map.class).get(property)
            .toString();
  }

}
