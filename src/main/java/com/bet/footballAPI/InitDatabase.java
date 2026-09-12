package com.bet.footballAPI;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bet.domain.Area4bet;
import com.bet.domain.Bet4bet;
import com.bet.domain.Competition4bet;
import com.bet.domain.Game4bet;
import com.bet.domain.GameMarket;
import com.bet.domain.Team4bet;
import com.bet.domain.User;
import com.bet.domain.enums.BetType;
import com.bet.domain.enums.MarketType;
import com.bet.domain.enums.Perfil;
import com.bet.domain.enums.RunnerType;
import com.bet.repository.AreaRepository;
import com.bet.repository.BetRepository;
import com.bet.repository.CompetitionRepository;
import com.bet.repository.GameRepository;
import com.bet.repository.TeamRepository;
import com.bet.repository.UserRepository;
import com.bet.services.exceptions.my4BetException;

import fourbet.controller.CompetitionsController;
import fourbet.controller.CountryController;
import fourbet.controller.EventController;
import fourbet.controller.StandingController;
import fourbet.exception.InvalidParamException;
import fourbet.exception.ResponseException;
import fourbet.exception.StatusException;
import fourbet.model.country.Country;
import fourbet.model.event.Event;
import fourbet.model.league.League;
import fourbet.model.standing.Standing;

@Service
public class InitDatabase {

	@Autowired
	private CompetitionRepository competitionRepo;

	@Autowired
	private TeamRepository teamRepo;

	@Autowired
	private GameRepository gameRepo;

	@Autowired
	private AreaRepository areaRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	User user1, user2;

	public void initDatabase() {
		try {
			loadUser();
			Set<Area4bet> areas = initArea();
			Set<Competition4bet> allCompetitions = new HashSet<>();

			for (Area4bet area : areas) {
				allCompetitions.addAll(initCompetition(area));
			}

			for (Competition4bet competiton : allCompetitions) {
				initTeams(competiton);
			}

			initGames();
		} catch (NumberFormatException | StatusException | ResponseException | InvalidParamException
				| ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Set<Area4bet> initArea() throws StatusException, ResponseException {

		CountryController countryController = new CountryController();
		List<Country> countries = countryController.getCountries();
		Set<Area4bet> areas = new HashSet<>();

		for (Country country : countries) {
			areas.add(new Area4bet(country.getCountryId(), country.getCountryName()));
		}

		areaRepo.saveAll(areas);

		return areas;

	}

	private Set<Competition4bet> initCompetition(final Area4bet area)
			throws NumberFormatException, StatusException, ResponseException, InvalidParamException {

		CompetitionsController competitionsController = new CompetitionsController();
		List<League> leagues = competitionsController.getLeagues(Integer.parseInt(area.getIdFootballAPI()));
		Set<Competition4bet> competitions = new HashSet<>();

		for (League league : leagues) {
			competitions.add(new Competition4bet(league.getLeagueId(), league.getLeagueName(), area));
		}

		competitionRepo.saveAll(competitions);

		return competitions;

	}

	private void loadUser() {
		user1 = new User("johny", "johny", bCryptPasswordEncoder.encode("123"), "teste1@gmail.com");

		user2 = new User("admin", "admin", bCryptPasswordEncoder.encode("123"), "teste2@gmail.com");
		user2.addPerfil(Perfil.ADMIN);

		user1.setEnabled(true);
		user2.setEnabled(true);

		userRepo.saveAll(Arrays.asList(user1, user2));

	}

	@Autowired
	private BetRepository betRepo;

	private void initGames() throws StatusException, ResponseException, ParseException {
		EventController eventController = new EventController();
		List<Event> events = eventController.getEventsByDate(LocalDate.of(2019, Month.MARCH, 1),
				LocalDate.of(2019, Month.JULY, 31));
		List<Game4bet> games = new ArrayList<>();
		List<Bet4bet> bets = new ArrayList<>();

		for (Event event : events) {
			Team4bet homeTeam = teamRepo.findByName(event.getMatchHometeamName());
			Team4bet awayTeam = teamRepo.findByName(event.getMatchAwayteamName());
			Competition4bet competition = competitionRepo.findByidFootballAPI(event.getLeagueId());

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Long date = format.parse(event.getMatchDate() + " " + event.getMatchTime()).getTime();

			Game4bet game4bet = new Game4bet(event.getMatchId(), homeTeam, awayTeam, competition, date,
					event.getMatchStatus(), null);

			loadBaseGameMarketRunners(game4bet);

			if (!event.getMatchStatus().equals("")) {
				finishGame(event, game4bet);
			}

			games.add(game4bet);

			Bet4bet bet1 = new Bet4bet();
			bet1.setMarket(MarketType.FULL_TIME_WINNER);
			bet1.setRunner(RunnerType.DRAW);
			bet1.setGame(game4bet);
			bet1.setBetType(BetType.PRIVATE);

			bet1.setUser(user1);
			user1.getBets().add(bet1);
			bets.add(bet1);
		}

		gameRepo.saveAll(games);
		betRepo.saveAll(bets);
	}

	private void loadBaseGameMarketRunners(final Game4bet game) {

		for (MarketType market : MarketType.getNormalMarketType()) {
			GameMarket gameMarketFullTimeWinner = new GameMarket(market);

			game.getGameMarkets().add(gameMarketFullTimeWinner);
		}
	}

	private void finishGame(Event gameAPI, Game4bet game) {

		Integer fullTimeScoreHomeTeam = gameAPI.getMatchHometeamScore().equals("") ? null
				: Integer.parseInt(gameAPI.getMatchHometeamScore());
		Integer fullTimeScoreAwayTeam = gameAPI.getMatchAwayteamScore().equals("") ? null
				: Integer.parseInt(gameAPI.getMatchAwayteamScore());

		Integer halfTimeScoreHomeTeam = gameAPI.getMatchHometeamHalftimeScore().equals("") ? null
				: Integer.parseInt(gameAPI.getMatchHometeamHalftimeScore());
		Integer halfTimeScoreAwayTeam = gameAPI.getMatchAwayteamHalftimeScore().equals("") ? null
				: Integer.parseInt(gameAPI.getMatchAwayteamHalftimeScore());

		for (GameMarket gameMarket : game.getGameMarkets()) {
			switch (gameMarket.getMarketType()) {
			case FULL_TIME_WINNER:
				if (fullTimeScoreHomeTeam != null && fullTimeScoreAwayTeam != null) {
					gameMarket.setRunnerWinner(calculateTimeWinner(game, fullTimeScoreHomeTeam, fullTimeScoreAwayTeam));
				}
				break;
			case HALF_TIME_WINNER:
				if (halfTimeScoreHomeTeam != null && halfTimeScoreAwayTeam != null) {
					gameMarket.setRunnerWinner(calculateTimeWinner(game, halfTimeScoreHomeTeam, halfTimeScoreAwayTeam));
				}
				break;
			case FULL_TIME_HOME_SCORE:
				if (fullTimeScoreHomeTeam != null) {
					gameMarket.setRunnerWinner(RunnerType.getRunnerScore(fullTimeScoreHomeTeam));
					game.setHomeScore(fullTimeScoreHomeTeam);
				}
				break;
			case FULL_TIME_AWAY_SCORE:
				if (fullTimeScoreAwayTeam != null) {
					gameMarket.setRunnerWinner(RunnerType.getRunnerScore(fullTimeScoreAwayTeam));
					game.setAwayScore(fullTimeScoreAwayTeam);
				}
				break;
			}
		}
	}

	private RunnerType calculateTimeWinner(Game4bet game, final int timeScoreHomeTeam, final int timeScoreAwayTeam) {

		if (timeScoreHomeTeam > timeScoreAwayTeam) {
			return RunnerType.HOME_TEAM;
		} else if (timeScoreHomeTeam < timeScoreAwayTeam) {
			return RunnerType.AWAY_TEAM;
		} else if (timeScoreHomeTeam == timeScoreAwayTeam) {
			return RunnerType.DRAW;
		}

		throw new my4BetException(
				"Erro no cálculo do vencedor do jogo " + game.toString() + " com os resultado da equipa da casa de "
						+ timeScoreHomeTeam + " e da equipa de fora de " + timeScoreAwayTeam);
	}

	public void initTeams(final Competition4bet competition)
			throws StatusException, ResponseException, InvalidParamException {
		StandingController standingController = new StandingController();
		List<Standing> standings = standingController.getStandings(Integer.parseInt(competition.getIdFootballAPI()));
		List<Team4bet> teams = new ArrayList<>();

		for (Standing standing : standings) {
			teams.add(new Team4bet(standing.getTeamName(), null, competition));
		}

		teamRepo.saveAll(teams);
	}

}
