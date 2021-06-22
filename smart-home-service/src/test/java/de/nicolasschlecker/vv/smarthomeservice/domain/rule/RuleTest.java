package de.nicolasschlecker.vv.smarthomeservice.domain.rule;

import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.Aktor;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.Sensor;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensordata.SensorData;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class RuleTest {
    @Test
    void testEquals() {
        final var rule = new Rule();
        rule.setName("Test");

        final var aktor = new Aktor();
        aktor.setName("Test");

        final var sensor = new Sensor();
        sensor.setName("Test");

        final var sensorData = new SensorData();
        sensorData.setCurrentValue(1);

        EqualsVerifier
                .simple()
                .withPrefabValues(Aktor.class, new Aktor(), aktor)
                .withPrefabValues(Rule.class, new Rule(), rule)
                .withPrefabValues(Sensor.class, new Sensor(), sensor)
                .withPrefabValues(SensorData.class, new SensorData(), sensorData)
                .forClass(Rule.class)
                .verify();
    }
}