package de.nicolasschlecker.vv.common.config;

import de.nicolasschlecker.vv.domain.exceptions.InvalidConfigurationException;
import de.nicolasschlecker.vv.domain.models.Config;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

public class EnvironmentConfigLoader implements IConfigLoader {
    private static final String PORT = "PORT";
    private static final String LOG_FILE = "LOG_FILE";
    private static final String LOG_LEVEL = "LOG_LEVEL";

    @Override
    public Config getConfig() throws InvalidConfigurationException {
        int port = getPort();
        var logFilePath = getLogFilePath();
        var logLevel = getLogLevel();

        return new Config(port, logFilePath, logLevel);
    }

    private int getPort() throws InvalidConfigurationException {
        var portString = getString(PORT);

        try {
            return Integer.parseInt(portString);
        } catch (NumberFormatException e) {
            throw new InvalidConfigurationException(String.format("Invalid value \"%s\" provided for \"%s\". Has to be a number.", portString, PORT));
        }
    }

    private Path getLogFilePath() throws InvalidConfigurationException {
        var logFileString = getString(LOG_FILE);

        try {
            return Paths.get(logFileString);
        } catch (InvalidPathException e) {
            throw new InvalidConfigurationException(String.format("Invalid value \"%s\" provided for \"%s\". Has to be a valid Path.", logFileString, LOG_FILE));
        }
    }

    private Level getLogLevel() throws InvalidConfigurationException {
        var logLevelString = getString(LOG_LEVEL);

        try {
            return Level.parse(logLevelString);
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new InvalidConfigurationException(String.format("Invalid value \"%s\" provided for \"%s\". Has to be one of: .", logLevelString, LOG_LEVEL));
        }
    }

    private String getString(String variable) throws InvalidConfigurationException {
        try {
            return System.getenv(variable);
        } catch (SecurityException e) {
            throw new InvalidConfigurationException("Could not access environment variable \"" + variable + "\" (" + e.getMessage() + ")");
        }
    }
}
