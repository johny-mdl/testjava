package com.bet.dto;

import java.util.Set;

import com.bet.domain.Bet4bet;
import com.bet.domain.Game4bet;

public class GameBetsDTO {

	private Game4bet game;
	private Set<Bet4bet> bets;

	public GameBetsDTO(Game4bet game, Set<Bet4bet> bets) {
		super();
		this.setGame(game);
		this.setBets(bets);
	}

	public Game4bet getGame() {
		return game;
	}

	public void setGame(Game4bet game) {
		this.game = game;
	}

	public Set<Bet4bet> getBets() {
		return bets;
	}

	public void setBets(Set<Bet4bet> bets) {
		this.bets = bets;
	}

}
