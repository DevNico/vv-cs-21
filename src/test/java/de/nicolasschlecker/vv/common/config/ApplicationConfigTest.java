package de.nicolasschlecker.vv.common.config;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class ApplicationConfigTest {
    @Test
    void getConfig_default() {
        final var config = ApplicationConfig.getConfig();
        assertThat(config, equalTo(ApplicationConfig.defaultConfig));
    }

    /*@Test
    void getConfig_environment() throws Exception {
        final var config = new Config(4242, Paths.get("logs_test.txt"), Level.INFO, Paths.get("measurement_test.json"));

        final var loadedConfig = withEnvironmentVariable(EnvironmentConfigLoader.PORT, String.valueOf(config.getPort()))
                .and(EnvironmentConfigLoader.LOG_FILE, config.getLogFilePath().toString())
                .and(EnvironmentConfigLoader.LOG_LEVEL, config.getLogLevel().toString())
                .and(EnvironmentConfigLoader.JSON_FILE, config.getJsonFilePath().toString()).execute(ApplicationConfig::getConfig);

        assertThat(loadedConfig, equalTo(config));
    }*/
}