package com.bet.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Area")
@AttributeOverride(name = "id", column = @Column(name = "ID_AREA"))
public class Area4bet extends BaseDomain {

	private String idFootballAPI;

	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "area")
	private Set<Competition4bet> competitions = new HashSet<>();

	public Area4bet() {
		super();
	}

	public Area4bet(String name) {
		this.name = name;
	}

	public Area4bet(String idFootballAPI, String name) {
		super();
		this.idFootballAPI = idFootballAPI;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdFootballAPI() {
		return idFootballAPI;
	}

	public void setIdFootballAPI(String idFootballAPI) {
		this.idFootballAPI = idFootballAPI;
	}

	public Set<Competition4bet> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(Set<Competition4bet> competitions) {
		this.competitions = competitions;
	}

	public void addCompetition(Competition4bet comp) {
		this.competitions.add(comp);
	}

}
