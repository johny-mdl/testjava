package com.bet.domain.enums;

import java.io.Serializable;
import java.util.EnumSet;

public enum RunnerType implements Serializable {
	HOME_TEAM, AWAY_TEAM, DRAW, ZERO, ONE, TWO, THREE, FOUR, FIVE;

	private static EnumSet<RunnerType> TEAM_WINNER = EnumSet.of(HOME_TEAM, AWAY_TEAM, DRAW);

	private static EnumSet<RunnerType> SCORE = EnumSet.of(ZERO, ONE, TWO, THREE, FOUR, FIVE);

	public static EnumSet<RunnerType> getTeamWinner() {
		return TEAM_WINNER;
	}

	public static EnumSet<RunnerType> getScore() {
		return SCORE;
	}

	public static RunnerType getRunnerScore(int score) {
		switch (score) {
		case 0:
			return RunnerType.ZERO;
		case 1:
			return RunnerType.ONE;
		case 2:
			return RunnerType.TWO;
		case 3:
			return RunnerType.THREE;
		case 4:
			return RunnerType.FOUR;
		case 5:
			return RunnerType.FIVE;
		default:
			return null;
		}
	}

}
