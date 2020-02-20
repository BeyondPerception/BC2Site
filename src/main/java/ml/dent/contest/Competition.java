package ml.dent.contest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import ml.dent.config.Configuration;
import ml.dent.user.Team;

public class Competition {

	private String competitionName;

	private Configuration config;

	private HashMap<Division, ArrayList<Team>> teams;

	public Competition(String competitionName) {
		this.competitionName = competitionName;
	}

	public String getCompName() {
		return competitionName;
	}

	public void setCompetitionName(String competitionName) {
		this.competitionName = competitionName;
	}

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}

	public Set<Division> getDivisions() {
		return teams.keySet();
	}

	public void addTeam(Team team, Division div) {
		if (teams.containsKey(div)) {
			teams.get(div).add(team);
		} else {
			teams.put(div, new ArrayList<Team>());
			teams.get(div).add(team);
		}
	}
}