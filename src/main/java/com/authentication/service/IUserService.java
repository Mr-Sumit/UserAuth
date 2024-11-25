package com.authentication.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.authentication.model.User;

public interface IUserService {
	public User createUser(User newUser);
	public List<User> getAllUsers();
	public User getUserById(long id) throws NoSuchElementException;
	public User updateUser(User user);
	public boolean deleteUserById(long id) throws IllegalArgumentException;
	public User getUserByEmail(String email) throws NoSuchElementException;
}
