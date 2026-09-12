package com.bet.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "GAME")
@AttributeOverride(name = "id", column = @Column(name = "ID_GAME"))
public class Game4bet extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -7761141151838474725L;

	private String idFootballAPI;

	@ManyToOne
	@JoinColumn(name = "ID_HOME_TEAM")
	private Team4bet homeTeam;

	@ManyToOne
	@JoinColumn(name = "ID_AWAY_TEAM")
	private Team4bet awayTeam;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ID_COMPETITION")
	private Competition4bet competition;

//	@JsonIgnore
//	@ManyToMany
//	@JoinTable(name = "GameMarket", joinColumns = @JoinColumn(name = "ID_GAME"), inverseJoinColumns = @JoinColumn(name = "ID_MARKET"))
//	private Set<Market4bet> markets = new HashSet<>();

//	@JsonIgnore
//	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
//	private Set<Bet4bet> bets = new HashSet<>();

//	@JsonIgnore
//	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
//	private Set<GameMarket4bet> gameMarkets = new HashSet<>();

//	@JsonIgnore
//	@ElementCollection
//	@CollectionTable(name = "GameMarket", joinColumns = @JoinColumn(name = "ID_GAME"))
//	private Map<MarketType, GameMarket> gameMarketsMap = new HashMap<>();

	@ElementCollection
	@CollectionTable(name = "GameMarket", joinColumns = @JoinColumn(name = "ID_GAME"))
	private Set<GameMarket> gameMarkets = new HashSet<>();

	private Long date;

	private Integer matchDay;

//	@JsonIgnore
//	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
//	private Set<GameRunner4bet> gameRunners = new HashSet<>();

	private String gameStatus;

	private Integer homeScore;

	private Integer awayScore;

	public Game4bet() {
	}

	public Game4bet(String idFootballAPI, Team4bet homeTeam, Team4bet awayTeam, Competition4bet competition, Long date,
			String gameStatus, Integer matchDay) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.competition = competition;
		this.date = date;
		this.gameStatus = gameStatus;
		this.idFootballAPI = idFootballAPI;
		this.matchDay = matchDay;
	}

	public String getIdFootballAPI() {
		return idFootballAPI;
	}

	public void setIdFootballAPI(String idFootballAPI) {
		this.idFootballAPI = idFootballAPI;
	}

	public Team4bet getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team4bet homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Team4bet getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team4bet awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Competition4bet getCompetition() {
		return competition;
	}

	public void setCompetition(Competition4bet competition) {
		this.competition = competition;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Integer getMatchDay() {
		return matchDay;
	}

	public void setMatchDay(Integer matchDay) {
		this.matchDay = matchDay;
	}

	public String getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}

	public Set<GameMarket> getGameMarkets() {
		return gameMarkets;
	}

	public void setGameMarkets(Set<GameMarket> gameMarkets) {
		this.gameMarkets = gameMarkets;
	}

	public Integer getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(Integer homeScore) {
		this.homeScore = homeScore;
	}

	public Integer getAwayScore() {
		return awayScore;
	}

	public void setAwayScore(Integer awayScore) {
		this.awayScore = awayScore;
	}

	// @JsonIgnore
//	public Set<GameMarket> getGameMarkets() {
//		return new HashSet<GameMarket>(gameMarketsMap.values());
//	}

}
