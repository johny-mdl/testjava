//package com.bet.domain;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.persistence.AttributeOverride;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.OneToMany;
//
//import com.bet.domain.enums.MarketType;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//@Entity(name = "Market")
//@AttributeOverride(name = "id", column = @Column(name = "ID_MARKET"))
//public class Market4bet extends BaseDomain {
//
//	@Enumerated(EnumType.STRING)
//	private MarketType marketType;
//
//	@JsonIgnore
//	@OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
//	private Set<MarketRunner4bet> marketRunner = new HashSet<>();
//
//	@JsonIgnore
//	@OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
//	private Set<GameMarket4bet> gameMarkets = new HashSet<>();
//
//	public Market4bet() {
//
//	}
//
//	public Market4bet(MarketType marketType) {
//		this.marketType = marketType;
//	}
//
//	public MarketType getMarketType() {
//		return marketType;
//	}
//
//	public void setMarketType(MarketType marketType) {
//		this.marketType = marketType;
//	}
//
//	public Set<MarketRunner4bet> getMarketRunner() {
//		return marketRunner;
//	}
//
//	public void setMarketRunner(Set<MarketRunner4bet> marketRunner) {
//		this.marketRunner = marketRunner;
//	}
//
//	public Set<GameMarket4bet> getGameMarkets() {
//		return gameMarkets;
//	}
//
//	public void setGameMarkets(Set<GameMarket4bet> gameMarkets) {
//		this.gameMarkets = gameMarkets;
//	}
//
//}
