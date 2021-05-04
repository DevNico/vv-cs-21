package de.nicolasschlecker.vv.net;

import de.nicolasschlecker.vv.domain.models.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MeasurementStateTest {
    private static final List<Message.Type> messageTypes = Arrays.asList(Message.Type.SENSOR_HELLO, Message.Type.ACKNOWLEDGE, Message.Type.MEASUREMENT, Message.Type.TERMINATE);

    static Stream<Arguments> measurementStateProvider() {
        Stream.Builder<Arguments> argumentBuilder = Stream.builder();
        for (final var state : MeasurementState.State.values()) {
            for (final var type : messageTypes) {
                argumentBuilder.add(Arguments.of(state, type));
            }
        }
        return argumentBuilder.build();
    }

    @ParameterizedTest()
    @MethodSource("measurementStateProvider")
    void transition(MeasurementState.State state, Message.Type type) {
        MeasurementState measurementState = new MeasurementState(state);
        measurementState.transition(type);
        assertThat(measurementState.getState(), equalTo(MeasurementState.TRANSITIONS[messageTypes.indexOf(type)][state.ordinal()]));
    }

    @Test
    void transition_withInvalidMessageType_shouldFail() {
        MeasurementState measurementState = new MeasurementState();
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> measurementState.transition(Message.Type.STATION_READY),
                "Expected transition(Message.Type.STATION_READY) to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Invalid Message.Type provided"));
    }

    @Test
    void transition_notApplied() {
        MeasurementState measurementState = new MeasurementState();
        final var state = measurementState.getState();
        measurementState.transition(Message.Type.SENSOR_HELLO, (from, to) -> false);
        assertThat(measurementState.getState(), equalTo(state));
    }
}