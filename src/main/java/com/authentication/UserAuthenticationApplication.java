package com.authentication;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import com.authentication.dao.IUserRepository;
import com.authentication.model.User;

@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class UserAuthenticationApplication {
	
	@Autowired
	private IUserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(UserAuthenticationApplication.class, args);
	}
	
	@PostConstruct
	public void createUser() {	
		User newUser = new User();
		newUser.setEmail("mr.sumitkr88@gmail.com");
		newUser.setMobileNumber(1234567089);
		newUser.setName("Sumit Kumar");
		newUser.setPassword("4***4**3");
		
		userRepository.save(newUser);
	}

}
