package com.bet.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bet.domain.Competition4bet;
import com.bet.dto.CompetitionDTO;
import com.bet.repository.BaseRepository;
import com.bet.repository.CompetitionRepository;
import com.bet.repository.DAO.CompetitionDAO;

@Service
public class CompetitionService extends BaseCRUDService<Competition4bet, CompetitionDTO> {

	@Autowired
	private CompetitionRepository competitionRepo;

	@Override
	public Competition4bet fromDTO(@Valid CompetitionDTO objDto) {
		Competition4bet competiton = new Competition4bet(objDto.getName());
		return competiton;
	}

	@Override
	protected BaseRepository<Competition4bet> getRepo() {
		return competitionRepo;
	}

	public List<Competition4bet> findByArea(String name) {
		return competitionRepo.findAll(CompetitionDAO.findByArea(name));
	}

}
