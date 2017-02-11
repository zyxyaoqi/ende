package com.ende.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderUtil {
	private final static int ENCODE_LEN = 4;
	
	public static PasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder(ENCODE_LEN);
	}
	public static String encodePassWord(String password) {
		return  new BCryptPasswordEncoder(ENCODE_LEN).encode(password);
	}

}
