package com.bet.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.bet.domain.enums.BetType;
import com.bet.domain.enums.MarketType;
import com.bet.domain.enums.RunnerType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "BET")
@AttributeOverride(name = "id", column = @Column(name = "ID_BET"))
public class Bet4bet extends BaseDomain {

	@NotNull
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "ID_USER")
	private User user;

//	@NotNull
//	@ManyToOne
//	@JoinColumn(name = "ID_GAME_MARKET_RUNNER")
//	private GameMarketRunner4bet gameMarketRunner;

//	@ManyToOne
//	@JoinColumn(name = "ID_MARKET_RUNNER")
//	private MarketRunner4bet marketRunner;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_GAME")
	private Game4bet game;

	@NotNull
	@Enumerated(EnumType.STRING)
	private MarketType market;

	@NotNull
	@Enumerated(EnumType.STRING)
	private RunnerType runner;

	@NotNull
	@Enumerated(EnumType.STRING)
	private BetType betType;

	private Boolean win;

	public Bet4bet() {

	}

	public Bet4bet(User user, Game4bet game, MarketType market, RunnerType runner) {
		this.user = user;
		this.game = game;
		this.market = market;
		this.runner = runner;
	}

	public Boolean getWin() {
		return win;
	}

	public void setWin(Boolean win) {
		this.win = win;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RunnerType getRunner() {
		return runner;
	}

	public void setRunner(RunnerType runner) {
		this.runner = runner;
	}

	public MarketType getMarket() {
		return market;
	}

	public void setMarket(MarketType market) {
		this.market = market;
	}

	public Game4bet getGame() {
		return game;
	}

	public void setGame(Game4bet game) {
		this.game = game;
	}

	public BetType getBetType() {
		return betType;
	}

	public void setBetType(BetType betType) {
		this.betType = betType;
	}

}
