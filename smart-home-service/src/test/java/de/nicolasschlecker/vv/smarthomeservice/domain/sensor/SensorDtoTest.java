package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class SensorDtoTest {
    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(SensorDto.class).verify();
    }
}