package com.bet.domain.enums;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

public enum MarketType implements Serializable {
	FULL_TIME_WINNER("Full Time Winner", RunnerType.getTeamWinner()),

	HALF_TIME_WINNER("Half Time Winner", RunnerType.getTeamWinner()),

	FULL_TIME_HOME_SCORE("Full Time Correct Score", RunnerType.getScore()),

	FULL_TIME_AWAY_SCORE("Half Time Correct Score", RunnerType.getScore());

	private static EnumSet<MarketType> NORMAL_MARKET_TYPE = EnumSet.of(FULL_TIME_WINNER, HALF_TIME_WINNER,
			FULL_TIME_HOME_SCORE, FULL_TIME_AWAY_SCORE);

	private String description;
	private Set<RunnerType> runners;

	MarketType(String description, Set<RunnerType> runner) {
		this.description = description;
		this.runners = runner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static Set<MarketType> getNormalMarketType() {
		return NORMAL_MARKET_TYPE;
	}

	public Set<RunnerType> getRunner() {
		return runners;
	}

	public void setRunner(Set<RunnerType> runners) {
		this.runners = runners;
	}

}
