package com.bet.dto;

import javax.validation.constraints.NotEmpty;

import com.bet.domain.Team4bet;

public class TeamDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1042727031222095612L;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String name;

	public TeamDTO() {
		super();
	}

	public TeamDTO(Team4bet team) {
		this.name = team.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
