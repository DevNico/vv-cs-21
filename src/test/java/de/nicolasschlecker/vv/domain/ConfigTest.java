package de.nicolasschlecker.vv.domain;

import de.nicolasschlecker.vv.domain.models.Config;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ApplicationConfig model tests")
class ConfigTest {
    private final Config config = new Config(3000, Paths.get("test.txt"), Level.INFO);

    @Test
    void testToString() {
        assertEquals("Config{port=3000, logFilePath=test.txt, logLevel=INFO}", config.toString());
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(Config.class).verify();
    }
}