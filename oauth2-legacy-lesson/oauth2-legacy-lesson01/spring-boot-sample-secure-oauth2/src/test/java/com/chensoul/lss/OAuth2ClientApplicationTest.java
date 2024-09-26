/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chensoul.lss;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Series of automated integration tests to verify proper behavior of auto-configured,
 * OAuth2-secured system
 *
 * @author user pass
 * @author Rob Winch
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OAuth2ClientApplicationTest {

  @Autowired
  private MockMvc mvc;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void everythingIsSecuredByDefault() throws Exception {
    this.mvc.perform(get("/").accept(MediaTypes.HAL_JSON)).andExpect(status().isUnauthorized());
    this.mvc.perform(get("/flights").accept(MediaTypes.HAL_JSON)).andExpect(status().isUnauthorized());
    this.mvc.perform(get("/flights/1").accept(MediaTypes.HAL_JSON)).andExpect(status().isUnauthorized());
    this.mvc.perform(get("/alps").accept(MediaTypes.HAL_JSON)).andExpect(status().isUnauthorized());
  }

  //    @Test
  public void accessingRootUriPossibleWithUserAccount() throws Exception {
    MockHttpServletRequestBuilder request = get("/").accept(MediaTypes.HAL_JSON)
            .with(httpBasic("user", "pass"));
    this.mvc.perform(request).andExpect(header().string("Content-Type", MediaTypes.HAL_JSON_VALUE))
            .andExpect(status().isOk()).andDo(print());
  }

  @Test
  public void useAppSecretsPlusUserAccountToGetBearerToken() throws Exception {
    MockHttpServletRequestBuilder tokenRequest = post("/oauth/token").with(httpBasic("foo", "bar"))
            .param("grant_type", "password").param("username", "user").param("password", "pass")
            .param("scope", "read");
    MvcResult result = this.mvc.perform(tokenRequest).andExpect(status().isOk()).andReturn();
    Object accessToken = this.objectMapper.readValue(result.getResponse().getContentAsString(), Map.class)
            .get("access_token");

    MockHttpServletRequestBuilder flightsRequest = get("/flights/1").accept(MediaTypes.HAL_JSON)
            .header("Authorization", "Bearer " + accessToken);
    MvcResult flightsAction = this.mvc.perform(flightsRequest)
            .andExpect(header().string("Content-Type", MediaTypes.HAL_JSON_VALUE)).andExpect(status().isOk())
            .andReturn();

    Flight flight = this.objectMapper.readValue(flightsAction.getResponse().getContentAsString(), Flight.class);

    assertThat(flight.getOrigin()).isEqualTo("Nashville");
    assertThat(flight.getDestination()).isEqualTo("Dallas");
    assertThat(flight.getAirline()).isEqualTo("Spring Ways");
    assertThat(flight.getFlightNumber()).isEqualTo("OAUTH2");
    assertThat(flight.getTraveler()).isEqualTo("user pass");
  }

}
