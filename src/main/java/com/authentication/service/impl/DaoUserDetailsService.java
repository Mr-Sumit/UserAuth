package com.authentication.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authentication.dao.IUserRepository;
import com.authentication.model.User;

@Service
public class DaoUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userFromDB = userRepository.findByName(username);
		UserDetails userDetailsObj = null;

		if (!userFromDB.isPresent()) {
			throw new UsernameNotFoundException(username + " is not Found in DB");
		}

		User dbUser = userFromDB.get();
		
		Collection<? extends GrantedAuthority> authorities = "admin".equalsIgnoreCase(dbUser.getName())
				? (Collection<? extends GrantedAuthority>) Arrays.asList(new SimpleGrantedAuthority("ADMIN"))
				: (Collection<? extends GrantedAuthority>) Arrays.asList(new SimpleGrantedAuthority("USER"));
		userDetailsObj = new org.springframework.security.core.userdetails.User(dbUser.getName(), dbUser.getPassword(),
				authorities);
		return userDetailsObj;
	}
}