package com.bet.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bet.domain.Competition4bet;

public interface CompetitionRepository
		extends BaseRepository<Competition4bet>, JpaSpecificationExecutor<Competition4bet> {

	Competition4bet findByidFootballAPI(String id);

}
