//package com.bet.domain;
//
//import javax.persistence.AttributeOverride;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//
//@Entity(name = "GAME_MARKET")
//@AttributeOverride(name = "id", column = @Column(name = "ID_GAME_MARKET"))
//public class GameMarket4bet extends BaseDomain {
//
//	@ManyToOne
//	@JoinColumn(name = "ID_MARKET")
//	private Market4bet market;
//
//	@ManyToOne
//	@JoinColumn(name = "ID_GAME")
//	private Game4bet game;
//
//	@ManyToOne
//	@JoinColumn(name = "ID_WINNER_RUNNER")
//	private Runner4bet winnerRunner;
//
//	public GameMarket4bet() {
//
//	}
//
//	public GameMarket4bet(Market4bet market, Game4bet game) {
//		this.market = market;
//		this.game = game;
//	}
//
//	public Market4bet getMarket() {
//		return market;
//	}
//
//	public void setMarket(Market4bet market) {
//		this.market = market;
//	}
//
//	public Game4bet getGame() {
//		return game;
//	}
//
//	public void setGame(Game4bet game) {
//		this.game = game;
//	}
//
//	public Runner4bet getWinnerRunner() {
//		return winnerRunner;
//	}
//
//	public void setWinnerRunner(Runner4bet winnerRunner) {
//		this.winnerRunner = winnerRunner;
//	}
//
//}
