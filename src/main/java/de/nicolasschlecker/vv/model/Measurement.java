package de.nicolasschlecker.vv.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.nicolasschlecker.vv.serialisation.LocalDateTimeAdapter;
import de.nicolasschlecker.vv.serialisation.MeasurementTypeAdapter;
import de.nicolasschlecker.vv.serialisation.MeasurementUnitAdapter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * The type Measurement.
 */
public class Measurement {
    /**
     * The Gson instance for json conversion of Measurements.
     */
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(MeasurementType.class, new MeasurementTypeAdapter())
            .registerTypeAdapter(MeasurementUnit.class, new MeasurementUnitAdapter()).create();

    /**
     * The {@link MeasurementType}
     */
    private final MeasurementType type;

    /**
     * The value
     */
    private final int value;

    /**
     * The {@link MeasurementUnit}
     */
    private final MeasurementUnit unit;

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
    public Measurement(MeasurementType type, int value, MeasurementUnit unit, LocalDateTime timestamp) {
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
    }

    /**
     * Instantiates a new Measurement with the timestamp initialized as the current time.
     *
     * @param type  the type
     * @param value the value
     * @param unit  the unit
     */
    public Measurement(MeasurementType type, int value, MeasurementUnit unit) {
        this(type, value, unit, ZonedDateTime
                .of(LocalDateTime.now(), ZoneId.systemDefault())
                .withZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime());
    }

    /**
     * From json measurement.
     *
     * @param json the json
     * @return the measurement
     */
    public static Measurement fromJson(String json) {
        return gson.fromJson(json, Measurement.class);
    }

    /**
     * To json string.
     *
     * @return the string
     */
    public String toJson() {
        return gson.toJson(this);
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public MeasurementType getType() {
        return type;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public MeasurementUnit getUnit() {
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
        return getValue() == that.getValue() && getType() == that.getType() && getUnit() == that.getUnit() && getTimestamp().equals(that.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getValue(), getUnit(), getTimestamp());
    }
}
