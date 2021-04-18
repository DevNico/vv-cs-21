package de.nicolasschlecker.vv.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Measurement model serialisation and equality tests")
class MeasurementTest {
    private final LocalDateTime dateTime = LocalDateTime.of(2021, 1, 1, 12, 0, 0);
    private final String json = "{\"type\":\"pressure\",\"value\":515,\"unit\":\"percent\",\"timestamp\":\"2021-01-01T12:00\"}";

    @Test
    void fromJson() {
        Measurement expected = new Measurement(MeasurementType.PRESSURE, 515, MeasurementUnit.PERCENT, dateTime);
        Measurement measurement = Measurement.fromJson(json);
        assertEquals(expected, measurement);
    }

    @Test
    void toJson() {
        Measurement measurement = new Measurement(MeasurementType.PRESSURE, 515, MeasurementUnit.PERCENT, dateTime);
        assertEquals(json, measurement.toJson());
    }

    @Test
    void getType() {
        Measurement measurement = new Measurement(MeasurementType.PRESSURE, 0, null);
        assertEquals(MeasurementType.PRESSURE, measurement.getType());
    }

    @Test
    void getValue() {
        Measurement measurement = new Measurement(null, 0, null);
        assertEquals(0, measurement.getValue());
    }

    @Test
    void getUnit() {
        Measurement measurement = new Measurement(null, 0, MeasurementUnit.CELSIUS);
        assertEquals(MeasurementUnit.CELSIUS, measurement.getUnit());
    }

    @Test
    void getTimestamp() {
        Measurement measurement = new Measurement(null, 0, null, dateTime);
        assertEquals(dateTime, measurement.getTimestamp());
    }

    @Test
    void getLocalTimestamp() {
        Measurement measurement = new Measurement(null, 0, null, dateTime);
        LocalDateTime localDateTime = ZonedDateTime
                .of(dateTime, ZoneId.of("UTC"))
                .toOffsetDateTime()
                .atZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
        assertEquals(localDateTime, measurement.getLocalTimestamp());
    }

    @Test
    void testToString() {
        Measurement measurement = new Measurement(MeasurementType.COUNT, 42, MeasurementUnit.KWH, dateTime);
        assertEquals("Measurement{type=COUNT, value=42, unit=KWH, timestamp=2021-01-01T12:00}", measurement.toString());
    }

    @Test
    void testEquals() {
        Measurement measurement1 = new Measurement(MeasurementType.COUNT, 42, MeasurementUnit.KWH, dateTime);
        Measurement measurement2 = new Measurement(MeasurementType.COUNT, 42, MeasurementUnit.KWH, dateTime);
        Measurement measurement3 = new Measurement(MeasurementType.COUNT, 43, MeasurementUnit.KWH, dateTime);

        assertEquals(measurement1, measurement1);
        assertEquals(measurement1, measurement2);
        assertNotEquals(measurement1, measurement3);
        assertNotEquals(measurement2, measurement3);
    }

    @Test
    void testHashCode() {
        Measurement measurement1 = new Measurement(MeasurementType.COUNT, 42, MeasurementUnit.KWH, dateTime);
        Measurement measurement2 = new Measurement(MeasurementType.COUNT, 42, MeasurementUnit.KWH, dateTime);
        Measurement measurement3 = new Measurement(MeasurementType.COUNT, 43, MeasurementUnit.KWH, dateTime);

        assertEquals(measurement1.hashCode(), measurement1.hashCode());
        assertEquals(measurement1.hashCode(), measurement2.hashCode());
        assertNotEquals(measurement1.hashCode(), measurement3.hashCode());
        assertNotEquals(measurement2.hashCode(), measurement3.hashCode());
    }
}