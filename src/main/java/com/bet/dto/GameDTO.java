package com.bet.dto;

import java.util.Date;

import com.bet.domain.Game4bet;

public class GameDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1042727031222095612L;

	private String idFootballAPI;

	private Integer homeTeam;

	private Integer awayTeam;

	private Integer competition;

	private Date date;

	public GameDTO() {
		super();
	}

	public GameDTO(Game4bet game) {
		this.homeTeam = game.getHomeTeam().getId();
		this.awayTeam = game.getAwayTeam().getId();
		this.competition = game.getCompetition().getId();
		this.idFootballAPI = game.getIdFootballAPI();
	}

	public Integer getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Integer homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Integer getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Integer awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Integer getCompetition() {
		return competition;
	}

	public void setCompetition(Integer competition) {
		this.competition = competition;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIdFootballAPI() {
		return idFootballAPI;
	}

	public void setIdFootballAPI(String idFootballAPI) {
		this.idFootballAPI = idFootballAPI;
	}

}
