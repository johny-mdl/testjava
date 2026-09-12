package com.bet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface APIRepository<ENTITY> extends JpaRepository<ENTITY, Integer> {

	ENTITY findByidFootballAPI(Long id);

}
