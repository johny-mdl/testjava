package com.bet.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

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
import com.bet.footballAPI.InitDatabase;
import com.bet.repository.AreaRepository;
import com.bet.repository.BetRepository;
import com.bet.repository.CompetitionRepository;
import com.bet.repository.GameRepository;
import com.bet.repository.TeamRepository;
import com.bet.repository.UserRepository;
import com.bet.services.exceptions.my4BetException;

import jfdata.enums.Plan;
import jfdata.manager.JfdataManager;
import jfdata.model.competition.Competition;
import jfdata.model.competition.CompetitionList;
import jfdata.model.match.Match;
import jfdata.model.match.MatchList;
import jfdata.model.match.MatchTime;
import jfdata.model.team.Team;
import jfdata.model.team.TeamList;

@Service
public class DBService {

	@Autowired
	private CompetitionRepository competitionRepo;

	@Autowired
	private TeamRepository teamRepo;

	@Autowired
	private GameRepository gameRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AreaRepository areaRepo;

	@Autowired
	private BetRepository betRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private void loadBaseGameMarketRunners(final Game4bet game) {

		for (MarketType market : MarketType.getNormalMarketType()) {
			GameMarket gameMarketFullTimeWinner = new GameMarket(market);

			game.getGameMarkets().add(gameMarketFullTimeWinner);
		}
	}

	public void instantiateTestDatabase() {

		User user1 = new User("admin", "admin", bCryptPasswordEncoder.encode("123"), "admin@gmail.com");
		user1.addPerfil(Perfil.ADMIN);
		user1.setEnabled(true);

		User user2 = new User("johny", "johny", bCryptPasswordEncoder.encode("123"), "johny.mdl.pt@gmail.com");
		User user3 = new User("pedro", "pedro", bCryptPasswordEncoder.encode("123"), "pedro@gmail.com");
		user2.setEnabled(true);
		user3.setEnabled(true);

		userRepo.saveAll(Arrays.asList(user1, user2, user3));

		Area4bet area = new Area4bet("Portugal");

		Competition4bet competition = new Competition4bet("Primeira liga");
		area.addCompetition(competition);

		Team4bet team1 = new Team4bet("Porto", null, competition);
		Team4bet team2 = new Team4bet("Benfica", null, competition);
		Team4bet team3 = new Team4bet("Braga", null, competition);
		Team4bet team4 = new Team4bet("Sporting", null, competition);

		Game4bet game1 = new Game4bet(null, team1, team2, competition, System.currentTimeMillis(), "", 1);
		Game4bet game2 = new Game4bet(null, team3, team4, competition, System.currentTimeMillis(), "", 1);

		Bet4bet bet1 = new Bet4bet();
		bet1.setMarket(MarketType.FULL_TIME_WINNER);
		bet1.setRunner(RunnerType.DRAW);
		bet1.setGame(game1);
		bet1.setBetType(BetType.PRIVATE);

		Bet4bet bet2 = new Bet4bet();
		bet2.setMarket(MarketType.FULL_TIME_WINNER);
		bet2.setRunner(RunnerType.DRAW);
		bet2.setGame(game1);
		bet2.setBetType(BetType.PRIVATE);

		Bet4bet bet3 = new Bet4bet();
		bet3.setMarket(MarketType.FULL_TIME_WINNER);
		bet3.setRunner(RunnerType.DRAW);
		bet3.setGame(game2);
		bet3.setBetType(BetType.PRIVATE);

		GameMarket gameMarket1 = new GameMarket(MarketType.FULL_TIME_WINNER);
		GameMarket gameMarket2 = new GameMarket(MarketType.HALF_TIME_WINNER);
		game1.getGameMarkets().add(gameMarket1);
		game1.getGameMarkets().add(gameMarket2);

		game2.getGameMarkets().add(gameMarket1);
		game2.getGameMarkets().add(gameMarket2);

		areaRepo.saveAll(Arrays.asList(area));
		competitionRepo.saveAll(Arrays.asList(competition));

		teamRepo.saveAll(Arrays.asList(team1, team2, team3, team4));

		gameRepo.saveAll(Arrays.asList(game1, game2));
		loadBaseGameMarketRunners(game1);
		loadBaseGameMarketRunners(game2);

		bet1.setUser(user2);
		user2.getBets().add(bet1);
		user2.getBets().add(bet1);

		bet2.setUser(user2);
		user2.getBets().add(bet2);
		user2.getBets().add(bet2);

		bet3.setUser(user2);
		user2.getBets().add(bet3);
		user2.getBets().add(bet3);

		betRepo.saveAll(Arrays.asList(bet1, bet2, bet3));
		userRepo.saveAll(Arrays.asList(user1, user2, user3));

		// user2.addFollower(user3);
		// userRepo.saveAll(Arrays.asList(user1, user2, user3));

	}

	User user1, user2;

	@Autowired
	private InitDatabase init;

	public void instantiateDevDatabase() {

		init.initDatabase();
		// JfdataManager jfdataManager = new
		// JfdataManager("7ca3338e8eaa42cca3b692d04c6c68f9");
		// loadCompetitions(jfdataManager);

	}

	private void loadCompetitions(JfdataManager jfdataManager) {
		CompetitionList allCompetitions = jfdataManager.getAllCompetitions(Plan.TIER_ONE);
		Set<Competition4bet> competitions = new HashSet<>();
		Set<Team4bet> teams = new HashSet<>();
		Set<Game4bet> games = new HashSet<>();

		for (Competition comp : allCompetitions.getCompetitions()) {
			if (comp.getId().equals("2017")) {
				competitions.add(new Competition4bet(comp));
				loadTeams(jfdataManager, comp, teams);
				break;
			}
		}

		competitionRepo.saveAll(competitions);
		teamRepo.saveAll(teams);

		for (Competition comp : allCompetitions.getCompetitions()) {
			if (comp.getId().equals("2017")) {
				try {
					loadMatches(jfdataManager, comp, games);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	}

	private void loadTeams(JfdataManager jfdataManager, Competition comp, Set<Team4bet> teams) {
		TeamList teamsByCompetition = jfdataManager.getTeamsByCompetition(Integer.parseInt(comp.getId()));

		for (Team team : teamsByCompetition.getTeams()) {
			teams.add(new Team4bet(team.getName(), Long.parseLong(team.getId()), null));
		}
	}

	private void loadMatches(JfdataManager jfdataManager, Competition comp, Set<Game4bet> games) throws ParseException {
		MatchList matchesByCompetition = jfdataManager.getMatchesByCompetition(Integer.parseInt(comp.getId()));

		for (Match game : matchesByCompetition.getMatches()) {
			Team4bet homeTeam = teamRepo.findByidFootballAPI(game.getHomeTeam().getId());
			Team4bet awayTeam = teamRepo.findByidFootballAPI(game.getAwayTeam().getId());
			Competition4bet competition = competitionRepo.findByidFootballAPI(comp.getId());

			// SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			// dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

			// 2018-08-10T19:30:00Z

			// DateTimeFormatter formatter =
			// DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			// LocalDateTime localDateTime = LocalDateTime.parse(game.getUtcDate(),
			// formatter);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date date = df.parse(game.getUtcDate());
			long epoch = date.getTime();

			Game4bet gamePersist = new Game4bet(game.getId(), homeTeam, awayTeam, competition, epoch, game.getStatus(),
					Integer.parseInt(game.getMatchday()));
			games.add(gamePersist);

			loadBaseGameMarketRunners(gamePersist);

			if (game.getStatus().equals("FINISHED")) {
				finishGame(game, gamePersist);
				gameRepo.saveAll(Arrays.asList(gamePersist));
			}
			gameRepo.saveAll(Arrays.asList(gamePersist));

			Bet4bet bet1 = new Bet4bet();
			bet1.setMarket(MarketType.FULL_TIME_WINNER);
			bet1.setRunner(RunnerType.DRAW);
			bet1.setGame(gamePersist);
			bet1.setBetType(BetType.PRIVATE);

			bet1.setUser(user1);
			user1.getBets().add(bet1);
			betRepo.saveAll(Arrays.asList(bet1));
		}
	}

	public void finishGame(Match gameAPI, Game4bet game) {
		MatchTime fullTime = gameAPI.getScore().getFullTime();
		MatchTime halfTime = gameAPI.getScore().getHalfTime();

		int fullTimeScoreHomeTeam = Integer.parseInt(fullTime.getHomeTeam());
		int fullTimeScoreAwayTeam = Integer.parseInt(fullTime.getAwayTeam());
		int halfTimeScoreHomeTeam = Integer.parseInt(halfTime.getHomeTeam());
		int halfTimeScoreAwayTeam = Integer.parseInt(halfTime.getAwayTeam());

		RunnerType fullTimeWinner = calculateTimeWinner(game, fullTimeScoreHomeTeam, fullTimeScoreAwayTeam);
		RunnerType halfTimeWinner = calculateTimeWinner(game, halfTimeScoreHomeTeam, halfTimeScoreAwayTeam);

		for (GameMarket gameMarket : game.getGameMarkets()) {
			switch (gameMarket.getMarketType()) {
			case FULL_TIME_WINNER:
				gameMarket.setRunnerWinner(fullTimeWinner);
				break;
			case HALF_TIME_WINNER:
				gameMarket.setRunnerWinner(halfTimeWinner);
				break;
			case FULL_TIME_HOME_SCORE:
				gameMarket.setRunnerWinner(RunnerType.getRunnerScore(fullTimeScoreHomeTeam));
				game.setHomeScore(fullTimeScoreHomeTeam);
				break;
			case FULL_TIME_AWAY_SCORE:
				gameMarket.setRunnerWinner(RunnerType.getRunnerScore(fullTimeScoreAwayTeam));
				game.setAwayScore(fullTimeScoreAwayTeam);
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

}
