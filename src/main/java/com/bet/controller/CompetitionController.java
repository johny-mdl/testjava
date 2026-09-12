package com.bet.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bet.domain.Competition4bet;
import com.bet.dto.BaseDTO;
import com.bet.dto.CompetitionDTO;
import com.bet.services.BaseCRUDService;
import com.bet.services.CompetitionService;

@RestController
@RequestMapping(value = "/competitions")
public class CompetitionController extends BaseController<Competition4bet, CompetitionDTO> {

	@Autowired
	private CompetitionService service;

	@Override
	BaseCRUDService<Competition4bet, CompetitionDTO> getService() {
		return service;
	}

	@Override
	BaseDTO getDTO(Competition4bet entity) {
		return new CompetitionDTO(entity);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Override
	public ResponseEntity<Void> insert(@Valid @RequestBody CompetitionDTO objDto) {
		return super.insert(objDto);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Override
	public ResponseEntity<Void> delete(Integer id) {
		return super.delete(id);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Override
	public ResponseEntity<Void> update(@Valid CompetitionDTO objDto, Integer id) {
		return super.update(objDto, id);
	}

	@RequestMapping(value = "/area/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<Competition4bet>> find(@PathVariable String name) {
		List<Competition4bet> obj = service.findByArea(name);
		return ResponseEntity.ok().body(obj);
	}
}
