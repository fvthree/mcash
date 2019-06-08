package com.fvthree.micromcashusers.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fvthree.micromcashusers.api.model.WebUsers;
import com.fvthree.micromcashusers.api.repository.UserDao;
import com.fvthree.micromcashusers.api.utils.EmailConstructor;

@Service
public class WebUserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EmailConstructor emailConstructor;
	
	@Autowired
	private JavaMailSender sender;
	
	public WebUsers registerUser(WebUsers user) {
		WebUsers savedUser = userDao.save(user);
		if (savedUser != null) {
			sender.send(emailConstructor.constructNewUserEmail(savedUser, user.getPassword()));
		}
		return savedUser;
	}
	
	public Optional<WebUsers> findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	public Optional<WebUsers> findById(String id) {
		return userDao.findById(Long.valueOf(id));
	}
	
	public List<WebUsers> findAll() {
		return userDao.findAll();
	}
}
