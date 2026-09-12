package com.bet.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bet.domain.Team4bet;
import com.bet.dto.BaseDTO;
import com.bet.dto.TeamDTO;
import com.bet.services.BaseCRUDService;
import com.bet.services.TeamService;

@RestController
@RequestMapping(value = "/teams")
public class TeamController extends BaseController<Team4bet, TeamDTO> {

	@Autowired
	private TeamService service;

	@Override
	BaseCRUDService<Team4bet, TeamDTO> getService() {
		return service;
	}

	@Override
	BaseDTO getDTO(Team4bet entity) {
		return new TeamDTO(entity);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Override
	public ResponseEntity<Void> insert(@Valid TeamDTO objDto) {
		return super.insert(objDto);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Override
	public ResponseEntity<Void> delete(Integer id) {
		return super.delete(id);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Override
	public ResponseEntity<Void> update(@Valid TeamDTO objDto, Integer id) {
		return super.update(objDto, id);
	}

}
