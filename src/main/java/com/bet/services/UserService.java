package com.bet.services;

import java.util.Calendar;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bet.domain.PasswordResetToken;
import com.bet.domain.User;
import com.bet.domain.VerificationToken;
import com.bet.dto.GameBetsDTO;
import com.bet.dto.PasswordDTO;
import com.bet.dto.UserDTO;
import com.bet.repository.BaseRepository;
import com.bet.repository.PasswordTokenRepository;
import com.bet.repository.UserRepository;
import com.bet.repository.VerificationTokenRepository;
import com.bet.security.UserSS;
import com.bet.services.exceptions.AuthorizationException;
import com.bet.services.exceptions.EventException;
import com.bet.services.exceptions.ObjectNotFoundException;
import com.bet.services.exceptions.PasswordException;
import com.bet.services.exceptions.TokenException;

@Service
public class UserService extends BaseCRUDService<User, UserDTO> {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private VerificationTokenRepository tokenRepository;

	@Autowired
	private PasswordTokenRepository passwordTokenRepository;

	@Autowired
	private BetService betsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private JavaMailSender mailSender;

	public User fromDTO(@Valid UserDTO objDto) {
		User user = new User(objDto.getUsername(), objDto.getName(), bCryptPasswordEncoder.encode(objDto.getPassword()),
				objDto.getEmail());
		return user;
	}

	public User findByUsername(String username) {
		User obj = userRepo.findByUsername(username);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Username: " + username + ", Tipo: " + User.class.getName());
		}
		return obj;
	}

	public User findByEmail(String email) {
		User obj = userRepo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Email: " + email + ", Tipo: " + User.class.getName());
		}
		return obj;
	}

	@Override
	protected BaseRepository<User> getRepo() {
		return userRepo;
	}

	public User findUserLogin() {
		UserSS user = UserSSService.authenticated();
		return super.find(user.getId());
	}

	@Transactional
	public void addMaster(Integer id) {
		UserSS userSS = UserSSService.authenticated();

		if (userSS == null) {
			throw new AuthorizationException("Acesso negado!");
		}

		User user = find(userSS.getId());
		User master = find(id);

		master.addFollower(user);
		insertOrUpdate(user);
		insertOrUpdate(master);
	}

	public Set<GameBetsDTO> getBets(Integer id) {
		return betsService.getGameBets(id);
	}

	public VerificationToken getVerificationToken(String VerificationToken) {
		return tokenRepository.findByToken(VerificationToken);
	}

	@Transactional
	public void createVerificationToken(User user, String token) {
		VerificationToken myToken = new VerificationToken(token, user);
		tokenRepository.save(myToken);
	}

	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordTokenRepository.save(myToken);
	}

	public void sendResetTokenEmail(String token, User user, String appUrl) throws EventException {
		String recipientAddress = user.getEmail();
		String subject = "Forgot password";
		String confirmationUrl = appUrl + "/#/resetPassword/" + token;
		String message = "Clique no endereço para recuperar a password ";

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message + " " + confirmationUrl);
		mailSender.send(email);
	}

	private PasswordResetToken validatePasswordResetToken(String token) {
		PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
		if (passToken == null) {
			throw new TokenException("Invalid token");
		}

		Calendar cal = Calendar.getInstance();
		if ((passToken.getExpiryDate() - cal.getTime().getTime()) <= 0) {
			throw new TokenException("Token expirado");
		}

//		UserDetails user = userDetailsService.loadUserByUsername(passToken.getUser().getUsername());
//		Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
//				Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
//		SecurityContextHolder.getContext().setAuthentication(auth);
		return passToken;
	}

	@Transactional
	public void changeUserPassword(PasswordDTO passwordDTO, String token) {
		User user = validatePasswordResetToken(token).getUser();

		if (!bCryptPasswordEncoder.matches(passwordDTO.getOldPassword(), user.getPassword())) {
			throw new PasswordException("Wrong password!");
		}

		user.setPassword(bCryptPasswordEncoder.encode(passwordDTO.getNewPassword()));
		insertOrUpdate(user);
	}

}
