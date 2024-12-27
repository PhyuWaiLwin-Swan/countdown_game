package com.example.countdown_game.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * A utility class for managing application configuration.
 * This class loads configuration properties from a specified file
 * and provides methods to retrieve these properties.
 *
 * The configuration file should be named `.env` and located in the root directory of the project.
 *
 */
public class Config {
    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    private static final String CONFIG_FILE = ".env";
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
            logger.info("Configuration loaded successfully from {}", CONFIG_FILE);
        } catch (IOException e) {
            logger.error("Failed to load configuration from {}: {}", CONFIG_FILE, e.getMessage());
        }
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     * Attempting to instantiate this class will throw an UnsupportedOperationException.
     */
    private Config() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    /**
     * Retrieves the API URL from the loaded configuration properties.
     *
     * @return the API URL as a String, or null if the property is not found.
     */
    public static String getApiUrl() {
        return properties.getProperty("API_URL");
    }
}
