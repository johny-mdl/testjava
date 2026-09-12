package com.bet.dto;

import com.bet.domain.Bet4bet;
import com.bet.domain.enums.MarketType;
import com.bet.domain.enums.RunnerType;

public class BetDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1042727031222095612L;

	private Integer idGame;
	private MarketType market;
	private RunnerType runner;

	public BetDTO() {
		super();
	}

	public BetDTO(Bet4bet bet) {
		this.idGame = bet.getGame().getId();
		this.market = bet.getMarket();
		this.runner = bet.getRunner();
	}

	public Integer getIdGame() {
		return idGame;
	}

	public void setIdGame(Integer idGame) {
		this.idGame = idGame;
	}

	public MarketType getMarket() {
		return market;
	}

	public void setMarket(MarketType market) {
		this.market = market;
	}

	public RunnerType getRunner() {
		return runner;
	}

	public void setRunner(RunnerType runner) {
		this.runner = runner;
	}

}
