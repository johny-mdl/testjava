package com.bet.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "TEAM")
public class Team4bet extends BaseDomain {

	private String name;

	private Long idFootballAPI;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "ID_COMPETITON")
	private Competition4bet competition;

	public Team4bet() {

	}

	public Team4bet(String name, Long idFootballAPI, Competition4bet competition) {
		this.name = name;
		this.idFootballAPI = idFootballAPI;
		this.competition = competition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIdFootballAPI() {
		return idFootballAPI;
	}

	public void setIdFootballAPI(Long idFootballAPI) {
		this.idFootballAPI = idFootballAPI;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFootballAPI == null) ? 0 : idFootballAPI.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team4bet other = (Team4bet) obj;
		if (idFootballAPI == null) {
			if (other.idFootballAPI != null)
				return false;
		} else if (!idFootballAPI.equals(other.idFootballAPI))
			return false;
		return true;
	}

	public Competition4bet getCompetition() {
		return competition;
	}

	public void setCompetition(Competition4bet competition) {
		this.competition = competition;
	}

}
