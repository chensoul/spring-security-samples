package com.chensoul.test;

import com.chensoul.lss.LssApp1;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


@SpringBootTest(classes = LssApp1.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class Lss1IntegrationTest {
	@Test
	public void whenLoadApplication_thenSuccess() {

	}
}
