package com.bet.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bet.services.exceptions.AuthorizationException;
import com.bet.services.exceptions.DataIntegrityException;
import com.bet.services.exceptions.EventException;
import com.bet.services.exceptions.ObjectNotFoundException;
import com.bet.services.exceptions.PasswordException;
import com.bet.services.exceptions.TokenException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Não encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Integridade de dados", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

		ValidationError err = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(),
				"Erro de validação", e.getMessage(), request.getRequestURI());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Acesso negado",
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}

	@ExceptionHandler(EventException.class)
	public ResponseEntity<StandardError> eventException(EventException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), 481, "Erro a criar evento", e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(481).body(err);
	}

	@ExceptionHandler(TokenException.class)
	public ResponseEntity<StandardError> tokenException(TokenException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), 480, "Erro no token de registo",
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(480).body(err);
	}

	@ExceptionHandler(PasswordException.class)
	public ResponseEntity<StandardError> passwordException(PasswordException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), 482, "Wrong password", e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(482).body(err);
	}

}
