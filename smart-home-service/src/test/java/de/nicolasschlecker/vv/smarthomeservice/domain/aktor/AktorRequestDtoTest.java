package de.nicolasschlecker.vv.smarthomeservice.domain.aktor;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class AktorRequestDtoTest {
    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(AktorRequestDto.class).verify();
    }
}