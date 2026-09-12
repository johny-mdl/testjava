package com.bet.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.bet.domain.enums.MarketType;
import com.bet.domain.enums.RunnerType;

@Embeddable
public class GameMarket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7294894150242226973L;

	@Enumerated(EnumType.STRING)
	private MarketType marketType;

	@Enumerated(EnumType.STRING)
	private RunnerType runnerWinner;

	public GameMarket() {
	}

	public GameMarket(MarketType marketType) {
		this.marketType = marketType;
	}

	public MarketType getMarketType() {
		return marketType;
	}

	public void setMarketType(MarketType marketType) {
		this.marketType = marketType;
	}

	public RunnerType getRunnerWinner() {
		return runnerWinner;
	}

	public void setRunnerWinner(RunnerType runnerWinner) {
		this.runnerWinner = runnerWinner;
	}

	public Set<RunnerType> getRunners() {
		return this.getMarketType().getRunner();
	}
}
