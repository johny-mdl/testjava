package com.bet.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bet.domain.Area4bet;
import com.bet.dto.AreaDTO;
import com.bet.repository.AreaRepository;
import com.bet.repository.BaseRepository;

@Service
public class AreaService extends BaseCRUDService<Area4bet, AreaDTO> {

	@Autowired
	private AreaRepository areaRepo;

	@Override
	public Area4bet fromDTO(@Valid AreaDTO objDto) {
		Area4bet area = new Area4bet(objDto.getName());
		return area;
	}

	@Override
	protected BaseRepository<Area4bet> getRepo() {
		return areaRepo;
	}

}
