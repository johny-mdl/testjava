package com.bet.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jfdata.model.competition.Competition;

@Entity(name = "COMPETITION")
@AttributeOverride(name = "id", column = @Column(name = "ID_COMPETITION"))
public class Competition4bet extends BaseDomain {

	private String name;

	private String idFootballAPI;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "ID_AREA")
	private Area4bet area;

	@OneToMany(mappedBy = "competition")
	@JsonIgnore
	private List<Game4bet> games = new ArrayList<>();

	@OneToMany(mappedBy = "competition")
	@JsonIgnore
	private List<Team4bet> teams = new ArrayList<>();

	public Competition4bet() {

	}

	public Competition4bet(String name) {
		this.name = name;
	}

	public Competition4bet(String idApi, String name, Area4bet area) {
		this.name = name;
		this.area = area;
		this.idFootballAPI = idApi;
	}

	public Competition4bet(Competition competition) {
		this.name = competition.getName();
		this.idFootballAPI = competition.getId();

		Area4bet area = new Area4bet(competition.getArea().getId(), competition.getArea().getName());
		this.area = area;
	}

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}

	public List<Game4bet> getGames() {
		return games;
	}

	public void setGames(List<Game4bet> games) {
		this.games = games;
	}

	public String getIdFootballAPI() {
		return idFootballAPI;
	}

	public void setIdFootballAPI(String idFootballAPI) {
		this.idFootballAPI = idFootballAPI;
	}

	public Area4bet getArea() {
		return area;
	}

	public void setArea(Area4bet area) {
		this.area = area;
	}

	public List<Team4bet> getTeams() {
		return teams;
	}

	public void setTeams(List<Team4bet> teams) {
		this.teams = teams;
	}

}
