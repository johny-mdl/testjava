package com.bet.repository;

import com.bet.domain.Team4bet;

public interface TeamRepository extends BaseRepository<Team4bet> {

	Team4bet findByidFootballAPI(String id);

	Team4bet findByName(String name);

}
