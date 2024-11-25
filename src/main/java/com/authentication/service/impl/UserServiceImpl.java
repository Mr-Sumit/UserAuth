package com.authentication.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.authentication.dao.IUserRepository;
import com.authentication.model.User;
import com.authentication.service.IUserService;
@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserRepository userRepository;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User createUser(User newUser) {
		//newUser.setPassword(UserPasswordEncrypter.encrypteUserPassword(newUser.getPassword()));
		newUser.setPassword(new BCryptPasswordEncoder(12).encode(newUser.getPassword()));
		return userRepository.save(newUser);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(long id){
		Optional<User> dbUserById = userRepository.findById(id);
		return dbUserById.orElseThrow(() -> new NoSuchElementException("Requested User is not present"));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User updateUser(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean deleteUserById(long id){
		userRepository.delete(this.getUserById(id));
		return true;

	}

	@Override
	public User getUserByEmail(String email) throws NoSuchElementException {
		Optional<User> dbUserByEmail = userRepository.findByEmail(email);
		return dbUserByEmail.orElseThrow(() -> new NoSuchElementException("Requested User is not present"));
		
	}
	
	

}
