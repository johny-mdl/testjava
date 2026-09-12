package com.bet.repository;

import com.bet.domain.User;
import com.bet.domain.VerificationToken;

public interface VerificationTokenRepository extends BaseRepository<VerificationToken> {

	VerificationToken findByToken(String token);

	VerificationToken findByUser(User user);
}
