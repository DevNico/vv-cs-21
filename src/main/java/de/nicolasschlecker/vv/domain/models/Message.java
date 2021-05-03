package de.nicolasschlecker.vv.domain.models;

import de.nicolasschlecker.vv.common.JsonFactory;

import java.util.Objects;

public final class Message {
    public static final Message STATION_HELLO = new Message(Message.Type.STATION_HELLO);
    public static final Message STATION_READY = new Message(Message.Type.STATION_READY);

    private final Type type;
    private final String payload;

    public Message(Type type) {
        this.type = type;
        this.payload = "";
    }

    public Message(Type type, String payload) {
        this.type = type;
        this.payload = payload;
    }

    public static Message fromJson(String json) {
        return JsonFactory.fromJson(json, Message.class);
    }

    public Type getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }

    public String toJson() {
        return JsonFactory.toJson(this);
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + getType() +
                ", payload='" + getPayload() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var message = (Message) o;
        return getType() == message.getType() && Objects.equals(getPayload(), message.getPayload());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getPayload());
    }

    public enum Type {
        SENSOR_HELLO("SensorHello"),
        STATION_HELLO("StationHello"),
        STATION_READY("StationReady"),
        ACKNOWLEDGE("Acknowledge"),
        MEASUREMENT("Measurement"),
        TERMINATE("Terminate"),
        TERMINATE_STATION("TerminateStation"),
        TIME_OUT("TimeOut");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
