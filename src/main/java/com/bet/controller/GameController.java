package com.bet.controller;
//test
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bet.domain.Game4bet;
import com.bet.domain.GameMarket;
import com.bet.dto.BaseDTO;
import com.bet.dto.GameDTO;
import com.bet.services.BaseCRUDService;
import com.bet.services.GameService;

@RestController
@RequestMapping(value = "/games")
public class GameController extends BaseController<Game4bet, GameDTO> {

	@Autowired
	private GameService service;

	BaseCRUDService<Game4bet, GameDTO> getService() {
		return service;
	}

	BaseDTO getDTO(Game4bet entity) {
		return new GameDTO(entity);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Override
	public ResponseEntity<Void> insert(@Valid GameDTO objDto) {
		return super.insert(objDto);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Override
	public ResponseEntity<Void> delete(Integer id) {
		return super.delete(id);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Override
	public ResponseEntity<Void> update(@Valid GameDTO objDto, Integer id) {
		return super.update(objDto, id);
	}

	@RequestMapping(value = "/competition/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Game4bet>> findAllByCompetitionDate(@PathVariable Integer id,
			@RequestParam(value = "date", required = false) Long date) {
		List<Game4bet> list = service.findAllByCompetitionDate(id, date);
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/competition/{id}/matchDay/{matchDay}", method = RequestMethod.GET)
	public ResponseEntity<List<Game4bet>> findAllByCompetitionMatchDay(@PathVariable Integer id,
			@PathVariable Integer matchDay) {
		List<Game4bet> list = service.findAllByCompetitionMatchDay(id, matchDay);
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/{id}/markets", method = RequestMethod.GET)
	public ResponseEntity<Set<GameMarket>> findMarketsByGame(@PathVariable Integer id) {
		Set<GameMarket> list = service.findMarketsByGame(id);
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/competition/{id}/nextGames", method = RequestMethod.GET)
	public ResponseEntity<List<Game4bet>> findNextGamesByCompetition(@PathVariable Integer id) {
		List<Game4bet> list = service.findNextGames(id);
		return ResponseEntity.ok().body(list);
	}

}
