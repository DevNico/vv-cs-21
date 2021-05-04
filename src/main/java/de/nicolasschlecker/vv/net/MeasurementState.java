package de.nicolasschlecker.vv.net;

import de.nicolasschlecker.vv.domain.models.Message;

import java.util.function.BiPredicate;

public class MeasurementState {
    protected static final State[][] TRANSITIONS = {
            // WAITING_FOR_CLIENT - WAITING_FOR_ACKNOWLEDGMENT - WAITING_FOR_MEASUREMENT - TERMINATED - ERROR
            {State.WAITING_FOR_ACKNOWLEDGMENT, State.ERROR, State.ERROR, State.TERMINATED, State.ERROR}, // SENSOR_HELLO
            {State.ERROR, State.WAITING_FOR_MEASUREMENT, State.ERROR, State.TERMINATED, State.ERROR}, // ACKNOWLEDGE
            {State.ERROR, State.ERROR, State.WAITING_FOR_MEASUREMENT, State.TERMINATED, State.ERROR}, // MEASUREMENT
            {State.ERROR, State.TERMINATED, State.TERMINATED, State.TERMINATED, State.TERMINATED}, // TERMINATE
    };
    private static final int TRANSITION_SENSOR_HELLO = 0;
    private static final int TRANSITION_ACKNOWLEDGE = 1;
    private static final int TRANSITION_MEASUREMENT = 2;
    private static final int TRANSITION_TERMINATE = 3;
    private State state;
    public MeasurementState() {
        this(State.WAITING_FOR_CLIENT);
    }

    public MeasurementState(State state) {
        this.state = state;
    }

    public void transition(Message.Type type) {
        transition(type, (from, to) -> true);
    }

    public void transition(Message.Type type, BiPredicate<State, State> transition) {
        int messageTypeIndex;

        if (type == Message.Type.SENSOR_HELLO) {
            messageTypeIndex = TRANSITION_SENSOR_HELLO;
        } else if (type == Message.Type.ACKNOWLEDGE) {
            messageTypeIndex = TRANSITION_ACKNOWLEDGE;
        } else if (type == Message.Type.MEASUREMENT) {
            messageTypeIndex = TRANSITION_MEASUREMENT;
        } else if (type == Message.Type.TERMINATE) {
            messageTypeIndex = TRANSITION_TERMINATE;
        } else {
            throw new IllegalArgumentException("Invalid Message.Type provided");
        }

        final var nextState = TRANSITIONS[messageTypeIndex][state.ordinal()];
        if (transition.test(state, nextState)) {
            state = nextState;
        }
    }

    public State getState() {
        return state;
    }

    public enum State {
        WAITING_FOR_CLIENT,
        WAITING_FOR_ACKNOWLEDGMENT,
        WAITING_FOR_MEASUREMENT,
        TERMINATED,
        ERROR
    }
}
