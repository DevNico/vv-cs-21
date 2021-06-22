package de.nicolasschlecker.vv.smarthomeservice.domain.sensordata;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class SensorDataDtoTest {
    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(SensorDataDto.class).verify();
    }
}