package de.nicolasschlecker.vv.domain.models;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Measurement model serialisation and equality tests")
class MeasurementTest {
    private final LocalDateTime dateTime = LocalDateTime.of(2021, 1, 1, 12, 0, 0);
    private final Measurement measurement = new Measurement(Measurement.Type.PRESSURE, 515, Measurement.Unit.PERCENT, dateTime);

    static Stream<Arguments> measurementsProvider() {
        Stream.Builder<Arguments> argumentBuilder = Stream.builder();
        for (final var type : Measurement.Type.values()) {
            for (final var unit : Measurement.Unit.values()) {
                argumentBuilder.add(Arguments.of(type, unit));
            }
        }
        return argumentBuilder.build();
    }

    @ParameterizedTest
    @MethodSource("measurementsProvider")
    void fromJson_toJson(Measurement.Type type, Measurement.Unit unit) {
        final var measurement = new Measurement(type, 42, unit, dateTime);
        final var json = measurement.toJson();
        final var fromJsonMeasurement = Measurement.fromJson(json);
        assertThat(measurement, equalTo(fromJsonMeasurement));
    }

    @Test
    void getLocalTimestamp() {
        LocalDateTime localDateTime = ZonedDateTime
                .of(dateTime, ZoneId.of("UTC"))
                .toOffsetDateTime()
                .atZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
        assertThat(localDateTime, equalTo(measurement.getLocalTimestamp()));
    }

    @ParameterizedTest
    @MethodSource("measurementsProvider")
    void testToString(Measurement.Type type, Measurement.Unit unit) {
        final var measurement = new Measurement(type, 42, unit, dateTime);
        assertThat(String.format("Measurement{type=%s, value=42, unit=%s, timestamp=2021-01-01T12:00}", type, unit), equalTo(measurement.toString()));
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(Measurement.class).verify();
    }
}