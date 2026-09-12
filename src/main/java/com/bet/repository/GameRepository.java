package com.bet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bet.domain.Competition4bet;
import com.bet.domain.Game4bet;

public interface GameRepository
		extends BaseRepository<Game4bet>, APIRepository<Game4bet>, JpaSpecificationExecutor<Game4bet> {

	List<Game4bet> findByMatchDayAndCompetition(Integer matchDay, Competition4bet competition);

}
