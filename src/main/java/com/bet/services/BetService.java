package com.bet.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bet.domain.Bet4bet;
import com.bet.domain.Game4bet;
import com.bet.domain.User;
import com.bet.domain.enums.Perfil;
import com.bet.dto.BetDTO;
import com.bet.dto.GameBetsDTO;
import com.bet.repository.BaseRepository;
import com.bet.repository.BetRepository;
import com.bet.security.UserSS;
import com.bet.services.exceptions.AuthorizationException;

@Service
public class BetService extends BaseCRUDService<Bet4bet, BetDTO> {

	@Autowired
	private BetRepository betRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private GameService gameService;

	@Override
	public Bet4bet fromDTO(@Valid BetDTO objDto) {
		UserSS userSS = UserSSService.authenticated();

		if (userSS == null) {
			throw new AuthorizationException("Acesso negado!");
		}
		User user = userService.find(userSS.getId());
		Game4bet game = gameService.find(objDto.getIdGame());

		Bet4bet bet = new Bet4bet(user, game, objDto.getMarket(), objDto.getRunner());
		return bet;
	}

	@Override
	protected BaseRepository<Bet4bet> getRepo() {
		return betRepo;
	}

	public List<Bet4bet> getBets(Integer userMasterId) {
		UserSS userLoginSS = UserSSService.authenticated();
		if (userLoginSS == null) {
			throw new AuthorizationException("Acesso negado!");
		}
		User userLogin = userService.find(userLoginSS.getId());

		if (userLogin.getId().equals(userMasterId)) {
			return userLogin.getBets();
		}

		User master = userService.find(userMasterId);
		boolean follower = master.isFollower(userLogin);

		if (userLoginSS.hasRole(Perfil.ADMIN) || follower) {
			return master.getBets();
		}
		return master.getPublicBets();
	}

	public Set<GameBetsDTO> getGameBets(Integer userMasterId) {
		Map<Game4bet, Set<Bet4bet>> gameBets = new HashMap<>();
		Set<GameBetsDTO> gameBetss = new HashSet<>();

		List<Bet4bet> bets = getBets(userMasterId);

		for (Bet4bet bet : bets) {
			Game4bet game = bet.getGame();
			if (gameBets.containsKey(game)) {
				gameBets.get(game).add(bet);
			} else {
				gameBets.put(game, new HashSet<>(Arrays.asList(bet)));
			}
		}

		for (Map.Entry<Game4bet, Set<Bet4bet>> entry : gameBets.entrySet()) {
			Game4bet game = entry.getKey();
			Set<Bet4bet> betsFromGame = entry.getValue();

			gameBetss.add(new GameBetsDTO(game, betsFromGame));

		}
		return gameBetss;
	}

}
