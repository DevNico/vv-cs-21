package de.nicolasschlecker.vv.domain.models;


import de.nicolasschlecker.vv.common.JsonFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Measurement {
    /**
     * The {@link Type}
     */
    private final Type type;
    /**
     * The value
     */
    private final long value;
    /**
     * The {@link Unit}
     */
    private final Unit unit;
    /**
     * The timestamp in UTC
     */
    private final LocalDateTime timestamp;

    /**
     * Instantiates a new Measurement.
     *
     * @param type      the type
     * @param value     the value
     * @param unit      the unit
     * @param timestamp the timestamp
     */
    public Measurement(Type type, int value, Unit unit, LocalDateTime timestamp) {
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
    }

    public static Measurement fromJson(String json) {
        return JsonFactory.fromJson(json, Measurement.class);
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public long getValue() {
        return value;
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * Gets timestamp in UTC.
     *
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Gets timestamp in the local Timezone.
     *
     * @return the timestamp
     */
    public LocalDateTime getLocalTimestamp() {
        return ZonedDateTime
                .of(timestamp, ZoneId.of("UTC"))
                .toOffsetDateTime()
                .atZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public String toJson() {
        return JsonFactory.toJson(this);
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "type=" + type +
                ", value=" + value +
                ", unit=" + unit +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measurement that = (Measurement) o;
        return getValue() == that.getValue() && getType() == that.getType() && getUnit() == that.getUnit() && Objects.equals(getTimestamp(), that.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getValue(), getUnit(), getTimestamp());
    }

    public enum Unit {
        CELSIUS("celsius"),
        KELVIN("kelvin"),
        PERCENT("percent"),
        HECTOPASCAL("hectopascal"),
        UNITS("units"),
        CMS("cms"),
        KWH("kwh");

        private final String name;

        Unit(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum Type {
        TEMPERATURE("temperature"),
        PRESSURE("pressure"),
        COUNT("count"),
        FLOW_RATE("flow_rate"),
        ENERGY("energy");

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
