package de.nicolasschlecker.vv.smarthomeservice.domain.rule;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class RuleRequestDtoTest {
    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(RuleRequestDto.class).verify();
    }
}