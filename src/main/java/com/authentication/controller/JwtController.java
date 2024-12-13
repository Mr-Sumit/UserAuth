package com.authentication.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.model.User;
import com.authentication.service.impl.DaoUserDetailsService;
import com.authentication.service.impl.JwtService;

@RestController
@RequestMapping("/jwt/token")
public class JwtController {
	private static Logger log = LoggerFactory.getLogger(JwtController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private DaoUserDetailsService daoUserDetailsService;
	
	@PostMapping("/generate")
	public String generateToken(@RequestBody User user) {
		log.info("generateToken for User : {}", user);
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
		return authentication.isAuthenticated() ? jwtService.generateToken(user.getName()) : "User info is not found";
	}
	
	@PostMapping("/validate")
	public boolean validateToken(@RequestBody String jwtoken) {
		log.info("Validate Token : {}", jwtoken);
		JSONObject reqToken = new JSONObject(jwtoken);
		String tokenUserName = jwtService.extractUserNameFromToken(reqToken.getString("token"));
		if (tokenUserName != null) {
			UserDetails userDetails = daoUserDetailsService.loadUserByUsername(tokenUserName);
			return jwtService.validateToken(reqToken.getString("token"), tokenUserName, userDetails);
		}
		return false;
	}
}
