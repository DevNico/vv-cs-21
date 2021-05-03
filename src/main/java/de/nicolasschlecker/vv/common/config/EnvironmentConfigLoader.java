package de.nicolasschlecker.vv.common.config;

import de.nicolasschlecker.vv.domain.exceptions.InvalidConfigurationException;
import de.nicolasschlecker.vv.domain.models.Config;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

public class EnvironmentConfigLoader implements IConfigLoader {
    public static final String PORT = "PORT";
    public static final String LOG_FILE = "LOG_FILE";
    public static final String LOG_LEVEL = "LOG_LEVEL";
    public static final String JSON_FILE = "JSON_FILE";

    @Override
    public Config getConfig(Config defaultConfig) throws InvalidConfigurationException {
        int port = getPort(defaultConfig.getPort());
        var logFilePath = getPath(LOG_FILE, defaultConfig.getLogFilePath());
        var logLevel = getLogLevel(defaultConfig.getLogLevel());
        var jsonFilePath = getPath(JSON_FILE, defaultConfig.getJsonFilePath());

        return new Config(port, logFilePath, logLevel, jsonFilePath);
    }

    private int getPort(int defaultPort) throws InvalidConfigurationException {
        var portString = getString(PORT, String.valueOf(defaultPort));

        try {
            return Integer.parseInt(portString);
        } catch (NumberFormatException e) {
            throw new InvalidConfigurationException(String.format("Invalid value \"%s\" provided for \"%s\". Has to be a number.", portString, PORT));
        }
    }

    private Path getPath(String variable, Path defaultPath) throws InvalidConfigurationException {
        var pathString = getString(variable, defaultPath.toString());

        try {
            return Paths.get(pathString);
        } catch (InvalidPathException e) {
            throw new InvalidConfigurationException(String.format("Invalid value \"%s\" provided for \"%s\". Has to be a valid Path.", pathString, variable));
        }
    }

    private Level getLogLevel(Level defaultLogLevel) throws InvalidConfigurationException {
        var logLevelString = getString(LOG_LEVEL, defaultLogLevel.toString());

        try {
            return Level.parse(logLevelString);
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new InvalidConfigurationException(String.format("Invalid value \"%s\" provided for \"%s\". Has to be one of: .", logLevelString, LOG_LEVEL));
        }
    }

    private String getString(String variable, String defaultValue) throws InvalidConfigurationException {
        try {
            return System.getProperty(variable, defaultValue);
        } catch (SecurityException e) {
            throw new InvalidConfigurationException("Could not access environment variable \"" + variable + "\" (" + e.getMessage() + ")");
        }
    }
}
