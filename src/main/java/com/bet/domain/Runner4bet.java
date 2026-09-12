//package com.bet.domain;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.persistence.AttributeOverride;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//
//import com.bet.domain.enums.RunnerType;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//@Entity(name = "RUNNER")
//@AttributeOverride(name = "id", column = @Column(name = "ID_RUNNER"))
//public class Runner4bet extends BaseDomain {
//
//	@JsonIgnore
//	@ManyToOne
//	@JoinColumn(name = "ID_MARKET")
//	private Market4bet market;
//
//	@Enumerated(EnumType.STRING)
//	private RunnerType runnerType;
//
//	@JsonIgnore
//	@OneToMany(mappedBy = "runner")
//	private Set<MarketRunner4bet> gameRunners = new HashSet<>();
//
//	@JsonIgnore
//	@OneToMany(mappedBy = "winnerRunner")
//	private Set<GameMarket4bet> gameMarkets = new HashSet<>();
//
//	public Runner4bet() {
//
//	}
//
//	public Runner4bet(RunnerType runnerType) {
//		this.runnerType = runnerType;
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
//	public RunnerType getRunnerType() {
//		return runnerType;
//	}
//
//	public void setRunnerType(RunnerType runnerType) {
//		this.runnerType = runnerType;
//	}
//
//	public Set<MarketRunner4bet> getGameRunners() {
//		return gameRunners;
//	}
//
//	public void setGameRunners(Set<MarketRunner4bet> gameRunners) {
//		this.gameRunners = gameRunners;
//	}
//
//}
