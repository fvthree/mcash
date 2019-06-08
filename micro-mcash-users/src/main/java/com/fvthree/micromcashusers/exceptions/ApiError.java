package com.fvthree.micromcashusers.exceptions;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiError {

	private HttpStatus status;
	private String message;
	private List<String> errors;
	
	public ApiError(final HttpStatus status, final String message, final List<String> errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	public ApiError(final HttpStatus status, final String message, final String error) {
		super();
		this.status = status;
		this.message = message;
		errors = Arrays.asList(error);
	}
}
