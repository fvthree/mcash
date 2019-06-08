package com.fvthree.micromcashusers.api.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

	public static String encode(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
		return encoder.encode(password);
	}
}
