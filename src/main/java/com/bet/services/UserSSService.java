package com.bet.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.bet.security.UserSS;

public class UserSSService {

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
