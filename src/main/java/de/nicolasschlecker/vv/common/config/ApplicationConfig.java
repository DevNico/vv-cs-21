package de.nicolasschlecker.vv.common.config;

import de.nicolasschlecker.vv.domain.models.Config;

import java.nio.file.Paths;
import java.util.logging.Level;

public class ApplicationConfig {
    private static Config config = new Config(3000, Paths.get("logs.txt"), Level.ALL);
    private static boolean defaultConfigUsed = true;

    private ApplicationConfig() {
    }

    public static void setConfig(Config config) {
        ApplicationConfig.config = config;
        ApplicationConfig.defaultConfigUsed = false;
    }

    public static Config getConfig() {
        if (config == null) {
            throw new IllegalStateException("Config has not been loaded.");
        }

        return config;
    }

    public static boolean isDefaultConfigUsed() {
        return defaultConfigUsed;
    }
}
