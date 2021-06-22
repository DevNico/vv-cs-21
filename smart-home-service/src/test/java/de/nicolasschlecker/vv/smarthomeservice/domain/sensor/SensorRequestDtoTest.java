package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class SensorRequestDtoTest {
    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(SensorRequestDto.class).verify();
    }
}