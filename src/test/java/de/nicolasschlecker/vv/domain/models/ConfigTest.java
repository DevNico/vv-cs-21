package de.nicolasschlecker.vv.domain.models;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.logging.Level;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Config model tests")
class ConfigTest {
    private final Config config = new Config(3000, Paths.get("test.txt"), Level.INFO, Paths.get("measurements.json"));

    @Test
    void testToString() {
        assertThat("Config{port=3000, logFilePath=test.txt, logLevel=INFO, jsonFilePath=measurements.json}", equalTo(config.toString()));
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(Config.class).verify();
    }
}