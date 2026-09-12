package com.bet.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bet.domain.Area4bet;
import com.bet.dto.AreaDTO;
import com.bet.dto.BaseDTO;
import com.bet.services.AreaService;
import com.bet.services.BaseCRUDService;

@RestController
@RequestMapping(value = "/areas")
public class AreaController extends BaseController<Area4bet, AreaDTO> {

	@Autowired
	private AreaService service;

	@Override
	BaseCRUDService<Area4bet, AreaDTO> getService() {
		return service;
	}

	@Override
	BaseDTO getDTO(Area4bet entity) {
		return new AreaDTO(entity);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Override
	public ResponseEntity<Void> insert(@Valid AreaDTO objDto) {
		return super.insert(objDto);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Override
	public ResponseEntity<Void> delete(Integer id) {
		return super.delete(id);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Override
	public ResponseEntity<Void> update(@Valid AreaDTO objDto, Integer id) {
		return super.update(objDto, id);
	}
}
