package ml.dent.user;

public class Team extends Loginable {

	private int points;

	private String teamName;

	public Team(String username, String password) {
		super(username, password);
	}

}
