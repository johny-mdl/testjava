package com.bet.api;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.Test;

import fourbet.controller.CompetitionsController;
import fourbet.controller.CountryController;
import fourbet.controller.EventController;
import fourbet.controller.HeadToHeadController;
import fourbet.controller.OddController;
import fourbet.controller.StandingController;
import fourbet.exception.InvalidParamException;
import fourbet.exception.ResponseException;
import fourbet.exception.StatusException;
import fourbet.model.country.Country;
import fourbet.model.event.Event;
import fourbet.model.head_to_head.HeadToHead;
import fourbet.model.league.League;
import fourbet.model.odds.Odd;
import fourbet.model.standing.Standing;

public class TestApi {

	// @Test
	public void testGetCountries() throws StatusException, ResponseException {
		CountryController countryController = new CountryController();
		List<Country> countries = countryController.getCountries();
		assertTrue(countries.size() > 0);
	}

	// @Test
	public void testGetCompetitions() throws StatusException, ResponseException, InvalidParamException {
		CompetitionsController competitionsController = new CompetitionsController();
		List<League> leagues = competitionsController.getLeagues(169);
		assertTrue(leagues.size() > 0);
	}

	// @Test
	public void testGetStandings() throws StatusException, ResponseException, InvalidParamException {
		StandingController standingController = new StandingController();
		List<Standing> standings = standingController.getStandings(128);
		assertTrue(standings.size() > 0);
	}

	// @Test
	public void testGetEventsByDate() throws StatusException, ResponseException {
		EventController eventController = new EventController();
		List<Event> events = eventController.getEventsByDate(LocalDate.of(2016, Month.SEPTEMBER, 30),
				LocalDate.of(2016, Month.NOVEMBER, 01));
		assertTrue(events.size() > 0);
	}

	// @Test
	public void testGetEventsByDateAndCountryId() throws StatusException, ResponseException, InvalidParamException {
		EventController eventController = new EventController();
		List<Event> events = eventController.getEventsByDateAndCountryId(LocalDate.of(2016, Month.SEPTEMBER, 30),
				LocalDate.of(2016, Month.NOVEMBER, 01), 169);
		assertTrue(events.size() > 0);
	}

	// @Test
	public void testGetEventsByDateAndLeagueId() throws StatusException, ResponseException, InvalidParamException {
		EventController eventController = new EventController();
		List<Event> events = eventController.getEventsByDateAndLeagueId(LocalDate.of(2016, Month.SEPTEMBER, 30),
				LocalDate.of(2016, Month.NOVEMBER, 01), 128);
		assertTrue(events.size() > 0);
	}

	// @Test
	public void testGetEventsByDateAndMatchId() throws StatusException, ResponseException, InvalidParamException {
		EventController eventController = new EventController();
		List<Event> events = eventController.getEventsByDateAndMatchId(LocalDate.of(2016, Month.SEPTEMBER, 30),
				LocalDate.of(2016, Month.NOVEMBER, 01), 119644);
		assertTrue(events.size() > 0);
	}

	// @Test
	public void testGetOddsByDate() throws StatusException, ResponseException {
		OddController oddController = new OddController();
		List<Odd> odds = oddController.getOddsByDate(LocalDate.of(2017, Month.FEBRUARY, 13),
				LocalDate.of(2017, Month.FEBRUARY, 13));
		assertTrue(odds.size() > 0);
	}

	// @Test
	public void testGetOddsByDateAndMatchId() throws StatusException, ResponseException, InvalidParamException {
		OddController oddController = new OddController();
		List<Odd> odds = oddController.getOddsByDateAndMatchId(LocalDate.of(2017, Month.FEBRUARY, 13),
				LocalDate.of(2017, Month.FEBRUARY, 13), 154391);
		assertTrue(odds.size() > 0);
	}

	@Test
	public void testGetHeadToHead() throws StatusException, ResponseException {
		HeadToHeadController headToHeadController = new HeadToHeadController();
		HeadToHead headToHead = headToHeadController.getHeadToHead("Chelsea", "Arsenal");
		assertTrue(headToHead != null);
	}

}
