package com.bet.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bet.domain.User;
import com.bet.domain.VerificationToken;
import com.bet.dto.BaseDTO;
import com.bet.dto.GameBetsDTO;
import com.bet.dto.PasswordDTO;
import com.bet.dto.UserDTO;
import com.bet.event.OnRegistrationCompleteEvent;
import com.bet.services.BaseCRUDService;
import com.bet.services.UserService;
import com.bet.services.exceptions.TokenException;

@RestController
@RequestMapping(value = "/users")
public class UserController extends BaseController<User, UserDTO> {

	@Autowired
	private UserService userService;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Override
	BaseCRUDService<User, UserDTO> getService() {
		return userService;
	}

	@Override
	BaseDTO getDTO(User entity) {
		return new UserDTO(entity);
	}

	@RequestMapping(value = "/username", method = RequestMethod.GET)
	public ResponseEntity<User> find(@RequestParam(value = "value") String username) {
		User obj = userService.findByUsername(username);
		return ResponseEntity.ok().body(obj);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Override
	public ResponseEntity<Void> delete(Integer id) {
		return super.delete(id);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Override
	public ResponseEntity<List<User>> findAll() {
		return super.findAll();
	}

	@RequestMapping(value = "/bets", method = RequestMethod.GET)
	public ResponseEntity<Set<GameBetsDTO>> findBets() {
		User user = userService.findUserLogin();
		Set<GameBetsDTO> bets = userService.getBets(user.getId());
		return ResponseEntity.ok().body(bets);
	}

	@RequestMapping(value = "/followers", method = RequestMethod.GET)
	public ResponseEntity<List<User>> findFollowers() {
		User user = userService.findUserLogin();
		List<User> followers = new ArrayList<>(user.getFollowers());
		return ResponseEntity.ok().body(followers);
	}

	@RequestMapping(value = "/followBy", method = RequestMethod.GET)
	public ResponseEntity<List<User>> findFollowBy() {
		User user = userService.findUserLogin();
		List<User> followers = new ArrayList<>(user.getFollowBy());
		return ResponseEntity.ok().body(followers);
	}

	@RequestMapping(value = "/addMaster/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> addMaster(@PathVariable Integer id) {
		userService.addMaster(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody UserDTO userDto, HttpServletRequest request) {
		User user = getService().fromDTO(userDto);
		user = getService().insertOrUpdate(user);

		String appUrl = request.getHeader("origin");
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/regitrationConfirm/{token}", method = RequestMethod.PUT)
	public ResponseEntity<Void> confirmRegistration(@PathVariable String token) {

		// Locale locale = request.getLocale();

		VerificationToken verificationToken = userService.getVerificationToken(token);
		if (verificationToken == null) {
			throw new TokenException("Invalid token");
		}

		Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate() - cal.getTime().getTime()) <= 0) {
			throw new TokenException("Token expirado");
		}

		User user = verificationToken.getUser();
		user.setEnabled(true);
		user = getService().insertOrUpdate(user);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/resetPassword/{email}", method = RequestMethod.POST)
	public ResponseEntity<Void> resetPassword(HttpServletRequest request, @PathVariable("email") String userEmail) {
		User user = userService.findByEmail(userEmail);
		String token = UUID.randomUUID().toString();

		userService.createPasswordResetTokenForUser(user, token);

		String appUrl = request.getHeader("origin");
		userService.sendResetTokenEmail(token, user, appUrl);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/changePassword/token/{token}", method = RequestMethod.PUT)
	public ResponseEntity<Void> showChangePasswordPage(@PathVariable("token") String token,
			@RequestBody PasswordDTO passwordDTO) {
//		String result = userService.validatePasswordResetToken(token);
//
//		if (result != null) {
//			throw new TokenException("Invalid token");
//		}
		userService.changeUserPassword(passwordDTO, token);

		return ResponseEntity.noContent().build();
	}

//	@RequestMapping(value = "/savePassword", method = RequestMethod.POST)
//	public ResponseEntity<Void> savePassword(@RequestBody String password) {
//		userService.changeUserPassword(password);
//		return ResponseEntity.noContent().build();
//	}

}
