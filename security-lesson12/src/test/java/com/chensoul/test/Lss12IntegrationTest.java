package com.chensoul.test;

import com.chensoul.lss.LssApp11;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

@SpringBootTest(classes = LssApp11.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class Lss12IntegrationTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private Filter springSecurityFilterChain;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
			.addFilters(springSecurityFilterChain)
			.build();
	}

	@Test
	public void whenLoadApplication_thenSuccess() {

	}

	@Test
	public void testUserLoginSuccess() throws Exception {
		ResultActions resultActions = mockMvc.perform(formLogin("/doLogin").user("user")
			.password("pass"));
		resultActions.andExpect(authenticated());
	}
}
