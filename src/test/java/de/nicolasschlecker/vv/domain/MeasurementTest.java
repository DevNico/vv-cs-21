package de.nicolasschlecker.vv.domain;

import de.nicolasschlecker.vv.domain.models.Measurement;
import de.nicolasschlecker.vv.domain.models.MeasurementType;
import de.nicolasschlecker.vv.domain.models.MeasurementUnit;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Measurement model serialisation and equality tests")
class MeasurementTest {
    private final LocalDateTime dateTime = LocalDateTime.of(2021, 1, 1, 12, 0, 0);
    private final Measurement measurement = new Measurement(MeasurementType.PRESSURE, 515, MeasurementUnit.PERCENT, dateTime);
    private final String json = "{\"type\":\"pressure\",\"value\":515,\"unit\":\"percent\",\"timestamp\":\"2021-01-01T12:00\"}";

    @Test
    void fromJson() {
        Measurement fromJsonMeasurement = Measurement.fromJson(json);
        assertEquals(measurement, fromJsonMeasurement);
    }

    @Test
    void toJson() {
        assertEquals(json, measurement.toJson());
    }

    @Test
    void getLocalTimestamp() {
        LocalDateTime localDateTime = ZonedDateTime
                .of(dateTime, ZoneId.of("UTC"))
                .toOffsetDateTime()
                .atZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
        assertEquals(localDateTime, measurement.getLocalTimestamp());
    }

    @Test
    void testToString() {
        assertEquals("Measurement{type=PRESSURE, value=515, unit=PERCENT, timestamp=2021-01-01T12:00}", measurement.toString());
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(Measurement.class).verify();
    }
}