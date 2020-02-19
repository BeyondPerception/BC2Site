package ml.dent.config;

import java.io.*;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.stream.Stream;

public class Configuration {
	// all config options should always have a value (no hidden defaults)
	// order config options from most to least useful / likely to be changed
	final private Properties properties;
	final private File file;

	public Configuration(Properties properties, File file) {
		this.file = file;
		this.properties = properties;
	}

	public Configuration(File file) throws IOException {
		if (!file.exists())
			throw new FileNotFoundException("Configuration file not found");
		this.file = file;
		this.properties = new Properties();
		this.properties.load(new FileReader(file));
	}

	public static Configuration createDefaultSiteConfiguration(File newConfig) throws IOException {
		Properties properties = new Properties();
		properties.setProperty("server.port", "" + Constants.DEFAULT_PORT);
		properties.setProperty("server.isServer", "true");
		properties.setProperty("divisions", "[None]");
		properties.setProperty("password.length", "" + Constants.DEFAULT_PASSWORD_LENGTH);
		properties.setProperty("points.right", "" + Constants.DEFAULT_POINTS_RIGHT);
		properties.setProperty("points.wrong", "" + Constants.DEFAULT_POINTS_WRONG);
		properties.setProperty("points.error", "" + Constants.DEFAULT_POINTS_ERROR);
		properties.setProperty("points.onlyLosePointsOnRight", "" + Constants.DEFAULT_ONLY_LOSE_POINTS_ON_RIGHT);
		properties.setProperty("points.combineIndividual", "" + Constants.DEFAULT_COMBINE_SCORES);
		Configuration config = new Configuration(properties, newConfig);
		config.save();
		return config;
	}

	public void save() throws IOException {
		PrintWriter writer = new PrintWriter(file);
		properties.store(writer, "BC2 Server Configuration Properties");
		writer.flush();
	}

	public Stream<String> getProblemProperties() {
		return properties.stringPropertyNames().stream().filter(x -> x.startsWith("problem"))
				.map(x -> x.substring(x.indexOf('.') + 1, x.lastIndexOf('.'))).distinct().sorted();
	}

	public String getProperty(String key) {
		String ret = properties.getProperty(key);
		if (ret != null)
			return ret;
		else
			throw new NoSuchElementException("Property does not exist");
	}

	public Integer getIntProperty(String key) {
		return Integer.parseInt(getProperty(key));
	}

	public Boolean getBoolProperty(String key) {
		return Boolean.parseBoolean(getProperty(key));
	}

	public String[] getArrayProperty(String key) {
		return getProperty(key).replaceAll("[\\[\\] ]", "").split(",");
	}

	public boolean isServer() {
		return Boolean.parseBoolean(properties.getProperty("server"));
	}

	public void setProperty(String key, Object value) {
		if (value instanceof String[]) {
			properties.setProperty(key, Arrays.toString((String[]) value));
		} else
			properties.setProperty(key, value.toString());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (String key : properties.stringPropertyNames()) {
			builder.append(key + ": " + properties.getProperty(key) + ", ");
		}
		builder.setLength(builder.length() - 2);
		return builder.toString();
	}
}
