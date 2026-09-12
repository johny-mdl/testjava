package com.bet.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bet.domain.Team4bet;
import com.bet.dto.TeamDTO;
import com.bet.repository.BaseRepository;
import com.bet.repository.TeamRepository;

@Service
public class TeamService extends BaseCRUDService<Team4bet, TeamDTO> {

	@Autowired
	private TeamRepository teamRepo;

	@Override
	public Team4bet fromDTO(@Valid TeamDTO objDto) {
		Team4bet team = new Team4bet(objDto.getName(), null, null);
		return team;
	}

	@Override
	protected BaseRepository<Team4bet> getRepo() {
		return teamRepo;
	}

}
