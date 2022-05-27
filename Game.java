package application;

import java.util.Date;

public class Game {
	private String gameDate;
	private String gameHour;
	private String teamName;
	private String competition;

	public Game() {

	}

	public Game(String gameDate, String gameHour, String teamName, String competition) {
		this.gameDate = gameDate;
		this.gameHour = gameHour;
		this.teamName = teamName;
		this.competition = competition;
	}

	public String getGameDate() {
		return gameDate;
	}

	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}

	public String getGameHour() {
		return gameHour;
	}

	public void setGameHour(String gameHour) {
		this.gameHour = gameHour;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getCompetition() {
		return competition;
	}

	public void setCompetition(String competition) {
		this.competition = competition;
	}

}
