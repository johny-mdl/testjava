package com.bet.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bet.controller.exception.FieldMessage;
import com.bet.domain.User;
import com.bet.dto.UserDTO;
import com.bet.repository.UserRepository;

public class UserInsertValidation implements ConstraintValidator<UserInsert, UserDTO> {

	@Autowired
	private UserRepository repo;

	@Override
	public boolean isValid(UserDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		User aux = repo.findByUsername(objDto.getUsername());
		if (aux != null) {
			list.add(new FieldMessage("username", "Username já existente"));
		}

		aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}
