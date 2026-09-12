//package com.bet.domain;
//
//import java.io.Serializable;
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.persistence.AttributeOverride;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//
//@Entity(name = "MARKET_RUNNER")
//@AttributeOverride(name = "id", column = @Column(name = "ID_MARKET_RUNNER"))
//public class MarketRunner4bet extends BaseDomain implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	// @Id
//	@ManyToOne
//	@JoinColumn(name = "ID_MARKET")
//	private Market4bet market;
//
//	// @Id
//	@ManyToOne
//	@JoinColumn(name = "ID_RUNNER")
//	private Runner4bet runner;
//
////	@JsonIgnore
////	@OneToMany(mappedBy = "marketRunner", cascade = CascadeType.ALL, orphanRemoval = true)
////	private Set<GameMarketRunner4bet> gameMarketRunner = new HashSet<>();
//
//	@OneToMany(mappedBy = "marketRunner", fetch = FetchType.LAZY)
//	private Set<Bet4bet> bets = new HashSet<>();
//
//	public MarketRunner4bet() {
//
//	}
//
//	public MarketRunner4bet(Market4bet market, Runner4bet runner) {
//		this.market = market;
//		this.runner = runner;
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
//	public Runner4bet getRunner() {
//		return runner;
//	}
//
//	public void setRunner(Runner4bet runner) {
//		this.runner = runner;
//	}
//
//	public Set<Bet4bet> getBets() {
//		return bets;
//	}
//
//	public void setBets(Set<Bet4bet> bets) {
//		this.bets = bets;
//	}
//
//}
