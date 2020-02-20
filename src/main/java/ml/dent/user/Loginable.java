package ml.dent.user;

public abstract class Loginable {
	private String username;
	private String password;

	public Loginable(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public boolean login(String username, String password) {
		return this.username == username && this.password == password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
