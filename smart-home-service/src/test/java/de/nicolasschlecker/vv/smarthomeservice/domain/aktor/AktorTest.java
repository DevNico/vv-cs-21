package de.nicolasschlecker.vv.smarthomeservice.domain.aktor;

import de.nicolasschlecker.vv.smarthomeservice.domain.rule.Rule;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.Sensor;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensordata.SensorData;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class AktorTest {
    @Test
    void testEquals() {
        final var rule = new Rule();
        rule.setName("Test");

        final var sensor = new Sensor();
        sensor.setName("Test");

        final var sensorData = new SensorData();
        sensorData.setCurrentValue(1);

        EqualsVerifier
                .simple()
                .withPrefabValues(Rule.class, new Rule(), rule)
                .withPrefabValues(Sensor.class, new Sensor(), sensor)
                .withPrefabValues(SensorData.class, new SensorData(), sensorData)
                .forClass(Aktor.class)
                .verify();
    }
}