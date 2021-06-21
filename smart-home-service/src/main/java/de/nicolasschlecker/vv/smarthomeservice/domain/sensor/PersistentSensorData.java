package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "sensor_data")
public class PersistentSensorData {
    @Id
    @GeneratedValue
    private Long id;

    private int currentValue;
    private Timestamp timestamp;
    private TemperatureUnit temperatureUnit;

    @OneToOne(mappedBy = "sensorData")
    private PersistentSensor sensor;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private Timestamp deletedAt;
}
