package com.example.demo;


import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

//@SpringBootTest
class DemoApplicationTests {

	private ApplicationContext applicationContext;
	@Test
	void contextLoads() {
		System.out.println(applicationContext.getBeanDefinitionCount());
	}

}
