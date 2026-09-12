package com.bet.repository;

import com.bet.domain.PasswordResetToken;
import com.bet.domain.User;

public interface PasswordTokenRepository extends BaseRepository<PasswordResetToken> {

	PasswordResetToken findByToken(String token);

	PasswordResetToken findByUser(User user);
}
