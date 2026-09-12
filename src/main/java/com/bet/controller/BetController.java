package com.bet.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bet.domain.Bet4bet;
import com.bet.dto.BaseDTO;
import com.bet.dto.BetDTO;
import com.bet.dto.GameBetsDTO;
import com.bet.services.BaseCRUDService;
import com.bet.services.BetService;

@RestController
@RequestMapping(value = "/bets")
public class BetController extends BaseController<Bet4bet, BetDTO> {

	@Autowired
	private BetService betService;

	@Override
	BaseCRUDService<Bet4bet, BetDTO> getService() {
		return betService;
	}

	@Override
	BaseDTO getDTO(Bet4bet entity) {
		return new BetDTO(entity);
	}

	@Override
	public ResponseEntity<Void> insert(@Valid @RequestBody BetDTO objDto) {
		return super.insert(objDto);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Override
	public ResponseEntity<Void> delete(Integer id) {
		return super.delete(id);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<Set<GameBetsDTO>> findBets(@PathVariable int id) {
		Set<GameBetsDTO> gameBets = betService.getGameBets(id);
		return ResponseEntity.ok().body(gameBets);
	}

}
