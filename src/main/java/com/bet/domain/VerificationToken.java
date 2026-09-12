package com.bet.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "ID_USER"))
public class VerificationToken extends Token {
	private static final int EXPIRATION = 1;

	public VerificationToken() {
		super();
	}

	public VerificationToken(String token, User user) {
		super(token, user);
	}

	@Override
	protected int getExpirationTime() {
		return EXPIRATION;
	}

}
