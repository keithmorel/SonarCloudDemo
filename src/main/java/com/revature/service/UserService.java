package com.revature.service;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.dao.UserDAO;
import com.revature.exception.UserNotFoundException;
import com.revature.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Transactional(rollbackFor = { UserNotFoundException.class })
	public User login(String username, String password) throws UserNotFoundException {
		
		try {
			return userDAO.getUserByUsernameAndPassword(username, password);
		} catch (NoResultException e) {
			throw new UserNotFoundException("User not found with the provided username and password");
		}
		
	}

}
