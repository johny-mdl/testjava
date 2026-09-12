package com.bet.dto;

import javax.validation.constraints.NotEmpty;

import com.bet.controller.validation.ValidEmail;
import com.bet.domain.User;
import com.bet.services.validation.UserInsert;

@UserInsert
public class UserDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4365734912909688606L;

	@NotEmpty
	private String username;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String name;

	@NotEmpty
	private String password;

	@ValidEmail
	@NotEmpty
	private String email;

	public UserDTO() {
		super();
	}

	public UserDTO(User user) {
		this.name = user.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
