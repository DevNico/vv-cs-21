package de.nicolasschlecker.vv.smarthomeservice.domain.sensordata;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class SensorDataRequestDtoTest {
    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(SensorDataRequestDto.class).verify();
    }
}