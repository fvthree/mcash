package com.fvthree.micromcashusers.api.web.requests.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class WebUserRegisterRequest {
	
	@NotNull
	@Size(min = 10, max = 20)
	private String username;
	
	@NotNull
	@Email
	private String email;
}
