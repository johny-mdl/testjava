package com.bet.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bet.domain.Competition4bet;
import com.bet.domain.Game4bet;
import com.bet.domain.GameMarket;
import com.bet.domain.Team4bet;
import com.bet.dto.GameDTO;
import com.bet.repository.BaseRepository;
import com.bet.repository.GameRepository;
import com.bet.repository.DAO.GameDAO;
import com.bet.util.DateUtils;

@Service
public class GameService extends BaseCRUDService<Game4bet, GameDTO> {

	@Autowired
	TeamService teamService;

	@Autowired
	CompetitionService competitionService;

	@Autowired
	private GameRepository gameRepo;

	@Override
	public Game4bet fromDTO(@Valid GameDTO objDto) {
		Team4bet homeTeam = teamService.find(objDto.getHomeTeam());
		Team4bet awayTeam = teamService.find(objDto.getAwayTeam());
		Competition4bet competition = competitionService.find(objDto.getCompetition());

		Game4bet game = new Game4bet(objDto.getIdFootballAPI(), homeTeam, awayTeam, competition, null, null, null);

		return game;
	}

	@Override
	protected BaseRepository<Game4bet> getRepo() {
		return gameRepo;
	}

	public List<Game4bet> findAllByCompetitionDate(Integer competitionId, Long date) {
		Competition4bet competition = competitionService.find(competitionId);

		return gameRepo.findAll(GameDAO.findAllByCompetitionDate(competition, date));
	}

	public List<Game4bet> findAllByCompetitionMatchDay(Integer competitionId, Integer matchDay) {
		Competition4bet competition = competitionService.find(competitionId);

		return gameRepo.findAll(GameDAO.findAllByCompetitionMatchDay(competition, matchDay));
	}

	public Set<GameMarket> findMarketsByGame(Integer id) {
		Game4bet game = find(id);
		return game.getGameMarkets();
	}

	public List<Game4bet> findNextGames(Integer idCompetition) {
		Competition4bet competition = competitionService.find(idCompetition);
		return gameRepo.findAll(GameDAO.findNextGames(competition));
	}

	public Set<GroupGame> findNextGamesGroupDate(Integer idCompetition) {
		Map<Long, List<Game4bet>> auxMap = new HashMap<>();

		Set<GroupGame> groupGames = new HashSet<>();

		Competition4bet competition = competitionService.find(idCompetition);
		Integer matchDay = gameRepo.findAll(GameDAO.findNextGames(competition)).get(0).getMatchDay();
		List<Game4bet> findByMatchDayAndCompetition = gameRepo.findByMatchDayAndCompetition(matchDay - 1, competition);

		for (Game4bet game : findByMatchDayAndCompetition) {
			Long date = DateUtils.miliTruncateDay(game.getDate());

			if (auxMap.containsKey(date)) {
				auxMap.get(date).add(game);
			} else {
				auxMap.put(date, new ArrayList<>(Arrays.asList(game)));
			}
		}

		for (Long date : auxMap.keySet()) {
			groupGames.add(new GroupGame(date, new ArrayList<>(auxMap.get(date))));
		}

		return groupGames;
	}

	public class GroupGame implements Serializable {

		private static final long serialVersionUID = 1L;
		private Long date;
		private List<Game4bet> games = new ArrayList<>();

		public GroupGame() {

		}

		public GroupGame(Long date, List<Game4bet> games) {
			super();
			this.date = date;
			this.games = games;
		}

		public Long getDate() {
			return date;
		}

		public void setDate(Long date) {
			this.date = date;
		}

		public List<Game4bet> getGames() {
			return games;
		}

		public void setGames(List<Game4bet> games) {
			this.games = games;
		}
	}

}
