package com.authentication.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dao.IUserRepository;
import com.authentication.model.User;
@Service
public class UserService implements IUserService {
	
	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public User createUser(User newUser) {
		return userRepository.save(newUser);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(long id) throws NoSuchElementException {
		Optional<User> dbUserById = userRepository.findById(id);
		return dbUserById.orElseThrow(NoSuchElementException::new);
	}

	@Override
	public User updateUser(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Override
	public boolean deleteUserById(long id) throws IllegalArgumentException  {
		userRepository.delete(this.getUserById(id));
		return true;

	}

	@Override
	public User getUserByEmail(String email) throws NoSuchElementException {
		Optional<User> dbUserByEmail = userRepository.findByEmail(email);
		return dbUserByEmail.orElseThrow(NoSuchElementException::new);
		
	}
	
	

}
