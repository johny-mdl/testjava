package com.bet.repository;

import javax.transaction.Transactional;

import com.bet.domain.User;

public interface UserRepository extends BaseRepository<User> {

	@Transactional
	User findByUsername(String username);

	User findByEmail(String email);
}
