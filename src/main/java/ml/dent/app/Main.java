package ml.dent.app;

import java.io.File;
import java.io.IOException;

import ml.dent.config.Configuration;
import ml.dent.config.Constants;
import ml.dent.net.NetworkServer;

public class Main {
	public void launch() {
//		printLogo();
		Configuration config = setupConfig();
		if (config == null) {
			System.exit(1);
		}

		startServer();
	}

	public void startServer() {
		NetworkServer server = new NetworkServer(Constants.DEFAULT_PORT);
		try {
			server.bind();
		} catch (InterruptedException e) {
			System.out.println("Failed to start the site");
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	public Configuration setupConfig() {
		File configFile = new File(Constants.DEFAULT_SERVER_CONFIG_LOCATION);
		if (!configFile.exists()) {
			try {
				return Configuration.createDefaultSiteConfiguration(configFile);
			} catch (IOException e) {
				System.out.println("Failed to make new default configuration file");
				System.out.println(e.getMessage());
				e.printStackTrace();
				return null;
			}
		} else {
			try {
				return new Configuration(configFile);
			} catch (IOException e) {
				System.out.println("Failed to load configuration file");
				System.out.println(e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
	}

	public void printLogo() {
		System.out.println(" ____                                                _             \n"
				+ "|  _ \\                                              (_)            \n"
				+ "| |_) |_ __ ___   __ _ _ __ __ _ _ __ ___  _ __ ___  _ _ __   __ _ \n"
				+ "|  _ <| '__/ _ \\ / _` | '__/ _` | '_ ` _ \\| '_ ` _ \\| | '_ \\ / _` |\n"
				+ "| |_) | | | (_) | (_| | | | (_| | | | | | | | | | | | | | | | (_| |\n"
				+ "|____/|_|  \\___/ \\__, |_|  \\__,_|_| |_| |_|_| |_| |_|_|_| |_|\\__, |\n"
				+ "                  __/ |                                       __/ |\n"
				+ "                 |___/                                       |___/ \n"
				+ "  _____            _            _      _____            _             _ \n"
				+ " / ____|          | |          | |    / ____|          | |           | |\n"
				+ "| |     ___  _ __ | |_ ___  ___| |_  | |     ___  _ __ | |_ _ __ ___ | |\n"
				+ "| |    / _ \\| '_ \\| __/ _ \\/ __| __| | |    / _ \\| '_ \\| __| '__/ _ \\| |\n"
				+ "| |___| (_) | | | | ||  __/\\__ \\ |_  | |___| (_) | | | | |_| | | (_) | |\n"
				+ " \\_____\\___/|_| |_|\\__\\___||___/\\__|  \\_____\\___/|_| |_|\\__|_|  \\___/|_|\n"
				+ "                                                                        \n"
				+ "                                                                        \n"
				+ "  _____           _                    ______   _____ /\\ _____  \n"
				+ " / ____|         | |                  / /  _ \\ / ____|/\\|__ \\ \\ \n"
				+ "| (___  _   _ ___| |_ ___ _ __ ___   | || |_) | |          ) | |\n"
				+ " \\___ \\| | | / __| __/ _ \\ '_ ` _ \\  | ||  _ <| |         / /| |\n"
				+ " ____) | |_| \\__ \\ ||  __/ | | | | | | || |_) | |____    / /_| |\n"
				+ "|_____/ \\__, |___/\\__\\___|_| |_| |_| | ||____/ \\_____|  |____| |\n"
				+ "         __/ |                        \\_\\                   /_/ \n"
				+ "        |___/                                                   ");
	}

	public static void main(String[] args) {
		new Main().launch();
	}
}
