package de.nicolasschlecker.vv.smarthomeservice.domain.sensordata;

import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.Sensor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "sensor_data")
public class SensorData {
    @Id
    @GeneratedValue
    private Long id;

    private int currentValue;
    private Timestamp timestamp;
    private TemperatureUnit temperatureUnit;

    @OneToOne(mappedBy = "sensorData")
    private Sensor sensor;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private Timestamp deletedAt;
}
