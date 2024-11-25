package com.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class UserAuthenticationApplication {
	
//	@Autowired
//	private IUserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(UserAuthenticationApplication.class, args);
	}
	
//	@PostConstruct
//	public void createUser() {	
//		User newUser = new User();
//		newUser.setEmail("admin@gmail.com");
//		newUser.setMobileNumber(1234567089);
//		newUser.setName("admin");
//		newUser.setPassword("admin"); 
//		userService.createUser(newUser);
//		//userRepository.save(newUser);
//	}

}
