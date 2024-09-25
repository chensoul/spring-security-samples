package com.chensoul.lss;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ResourceServerIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @Test
  public void givenJwt_whenHttpGet_thenOk() throws Exception {
    this.mvc.perform(get("/api/projects").with(jwt())
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", Matchers.greaterThan(0)));
  }

  @Test
  public void notGivenJwt_whenHttpGet_thenUnauthorized() throws Exception {
    this.mvc.perform(get("/api/projects"))
            .andExpect(status().isForbidden())
//            .andExpect(header().string(HttpHeaders.WWW_AUTHENTICATE, "Bearer"))
    ;
  }

  @Test
  public void givenJwtAndOnlyWriteScope_whenHttpGet_thenForbidden() throws Exception {
    this.mvc.perform(get("/api/projects").with(jwt().jwt(jwtBuilder -> jwtBuilder.claim("scope", "write")))
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(result -> System.out.println(result.getResponse().getHeader(HttpHeaders.WWW_AUTHENTICATE)))
            .andExpect(status().isForbidden())
    ;
  }

  @Test
  public void givenJwtAndReadScope_whenHttpGet_thenOk() throws Exception {
    this.mvc.perform(get("/api/projects").with(jwt().jwt(jwtBuilder -> jwtBuilder.claim("scope", "read")))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", Matchers.greaterThan(0)));
  }

  @Test
  public void givenJwtAndWriteScope_whenHttpPost_thenOk() throws Exception {
    this.mvc.perform(post("/api/projects").with(jwt().jwt(jwtBuilder -> jwtBuilder.claim("scope", "write")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"Project 1\",\"dateCreated\":\"2019-06-13\"}"))
            .andExpect(status().isCreated());
  }

  @Test
  public void givenJwtAndOnlyReadScope_whenHttpPost_thenForbidden() throws Exception {
    this.mvc.perform(post("/api/projects").with(jwt().jwt(jwtBuilder -> jwtBuilder.claim("scope", "read")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"id\":1,\"name\":\"Project 1\",\"dateCreated\":\"2019-06-13\"}"))
            .andExpect(status().isForbidden())
    ;
  }
}
