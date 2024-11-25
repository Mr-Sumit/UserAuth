package com.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.authentication.controller.UserController;
import com.authentication.model.User;
import com.authentication.service.IUserService;

@SpringBootTest
class UserAuthenticationApplicationTests {
	
	@InjectMocks
	UserController controller;
	
	@Mock
	IUserService service;
	
	@BeforeAll
	static void beforeClass() {
		System.out.println("BeforeAll");
	}
	
	@BeforeEach
	void before() {
		System.out.println("Before");
	}
	
	@AfterEach
	void after() {
		System.out.println("After");
	}
	
	@AfterAll
	static void afterAll() {
		System.out.println("AfterAll");
	}
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void test_createUser() {
		when(service.createUser(Mockito.any())).thenReturn(createUserStub());
		ResponseEntity<Void> resp = controller.createUser(createUserStub());
		assertEquals(201, resp.getStatusCodeValue());
	}
	
	private User createUserStub() {
		return new User();
	}

}
