package com.bet.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.bet.domain.Competition4bet;

public class CompetitionDTO extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1042727031222095612L;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String name;

	public CompetitionDTO() {
	}

	public CompetitionDTO(String name) {
		super();
		this.name = name;
	}

	public CompetitionDTO(Competition4bet competition) {
		this.name = competition.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
