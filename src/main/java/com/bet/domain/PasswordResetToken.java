package com.bet.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "ID_USER"))
public class PasswordResetToken extends Token {
	private static final int EXPIRATION = 10;

	public PasswordResetToken() {
		super();
	}

	public PasswordResetToken(String token, User user) {
		super(token, user);
	}

	@Override
	protected int getExpirationTime() {
		return EXPIRATION;
	}

}
