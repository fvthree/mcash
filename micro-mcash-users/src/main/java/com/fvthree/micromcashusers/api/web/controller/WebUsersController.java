package com.fvthree.micromcashusers.api.web.controller;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fvthree.micromcashusers.api.model.WebUsers;
import com.fvthree.micromcashusers.api.service.WebUserService;
import com.fvthree.micromcashusers.api.web.requests.model.WebUserRegisterRequest;

@RestController
public class WebUsersController {

	private final Logger log = LoggerFactory.getLogger(WebUsersController.class);
	
	@Autowired
	private WebUserService userService;
	
	@GetMapping("/user/{username}")
	public ResponseEntity<?> findByUsername(@PathVariable("username") String username) {
		Optional<WebUsers> webusers = userService.findByUsername(username);
		return new ResponseEntity<>(webusers.get(), HttpStatus.OK);
	}
	
	@GetMapping("/user/id/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") String id) {
		Optional<WebUsers> webusers = userService.findById(id);
		return new ResponseEntity<>(webusers.get(), HttpStatus.OK);
	}
	
	@PostMapping("/user/register")
	public ResponseEntity<?> register(@RequestBody @Valid WebUserRegisterRequest reqst) {
		log.debug("Request to save Web User: {}", reqst);
		WebUsers user = WebUsers.builder()
				.username(reqst.getUsername())
				.email(reqst.getEmail())
				.password(RandomStringUtils.randomAlphanumeric(10))
				.created(new Date())
				.updated(new Date())
				.build();
		
		user = userService.registerUser(user);
		
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
}
