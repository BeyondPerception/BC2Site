package ml.dent.config;

public class Constants {
	public static final String DEFAULT_IP = "localhost";
	public static final int DEFAULT_PORT = 29170;
	public static final String DEFAULT_SERVER_CONFIG_LOCATION = "server.properties";
	public static final String DEFAULT_DB_LOCATION = "bc2.db";
	public static final int DEFAULT_PASSWORD_LENGTH = 15;
	public static final int DEFAULT_POINTS_RIGHT = 60;
	public static final int DEFAULT_POINTS_WRONG = -5;
	public static final int DEFAULT_POINTS_ERROR = -5;
	public static final boolean DEFAULT_COMBINE_SCORES = true;
	public static final boolean DEFAULT_ONLY_LOSE_POINTS_ON_RIGHT = true;

	// stolen from stack overflow
	// i dont understand how this works
	public static final String SR = "[^\"\\s]+|\"(\\\\.|[^\\\\\"])*\"";
	
	private Constants() {};
}
