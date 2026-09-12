package com.bet.dto;

import javax.validation.constraints.NotEmpty;

import com.bet.domain.Area4bet;

public class AreaDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1042727031222095612L;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String name;

	public AreaDTO() {
		super();
	}

	public AreaDTO(Area4bet area) {
		this.name = area.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
