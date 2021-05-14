package com.revature.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.model.User;

@Repository
public class UserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public User getUserByUsernameAndPassword(String username, String password) {
		
		Session session = sessionFactory.getCurrentSession();
		
		User user = (User) session.createQuery("FROM User WHERE username=:un AND password=:pw")
				.setParameter("un", username)
				.setParameter("pw", password)
				.getSingleResult();
		return user;
		
	}

}
