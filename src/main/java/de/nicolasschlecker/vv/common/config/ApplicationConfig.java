package de.nicolasschlecker.vv.common.config;

import de.nicolasschlecker.vv.domain.exceptions.InvalidConfigurationException;
import de.nicolasschlecker.vv.domain.models.Config;

import java.nio.file.Paths;
import java.util.logging.Level;

public class ApplicationConfig {
    public static final Config defaultConfig = new Config(3000, Paths.get("logs.txt"), Level.ALL, Paths.get("measurements.json"));

    private static ApplicationConfig applicationConfig;
    private Config config;

    private ApplicationConfig() {
        final IConfigLoader configLoader = new EnvironmentConfigLoader();
        try {
            // Try to load configuration from the provided ConfigLoader
            config = configLoader.getConfig(defaultConfig);
        } catch (InvalidConfigurationException e) {
            config = defaultConfig;
        }
    }

    public static Config getConfig() {
        if (applicationConfig == null) {
            applicationConfig = new ApplicationConfig();
        }
        return applicationConfig.config;
    }
}
