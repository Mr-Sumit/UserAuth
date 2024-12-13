package com.authentication.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.model.User;
import com.authentication.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService;

	@GetMapping("/greet")
	public String greet(HttpServletRequest request) {
		return "Welcome to your App ";
	}

	@GetMapping("/csrf")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}

	@PostMapping("/newuser")
	public ResponseEntity<Void> createUser(@RequestBody User newUser) {
		log.info("newUser data in request : {}", newUser);
		userService.createUser(newUser);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		User dbUserById = userService.getUserById(id);
		return new ResponseEntity<User>(dbUserById, HttpStatus.OK);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
		User dbUserByEmail = userService.getUserByEmail(email);
		return new ResponseEntity<User>(dbUserByEmail, HttpStatus.OK);
	}

	@PostMapping("/update/{id}")
	public User updateUser(@PathVariable long id, @RequestBody User updateUser) {
		User dbUser = userService.getUserById(id);
		updateUser.setId(dbUser.getId());
		return userService.updateUser(updateUser);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deteteUser(@PathVariable int id) {
		boolean result = userService.deleteUserById(id);
		return result == true ? new ResponseEntity<Void>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

}
