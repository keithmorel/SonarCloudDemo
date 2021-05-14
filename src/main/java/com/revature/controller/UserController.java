package com.revature.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.revature.exception.UserNotFoundException;
import com.revature.model.User;
import com.revature.service.UserService;
import com.revature.template.LoginTemplate;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpServletRequest request;

	@PostMapping(path="login")
	public ResponseEntity<Void> login(@RequestBody LoginTemplate loginTemplate) {
		
		try {
			User user = userService.login(loginTemplate.getUsername(), loginTemplate.getPassword());
			
			HttpSession session = request.getSession(true);
			session.setAttribute("loggedInUser", user); // *** Look into JWT instead of this ***
			
			return ResponseEntity.status(200).build();
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(400).build();
		}
		
	}

}
