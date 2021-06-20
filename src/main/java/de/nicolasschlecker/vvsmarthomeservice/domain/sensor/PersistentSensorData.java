package de.nicolasschlecker.vvsmarthomeservice.domain.sensor;

import de.nicolasschlecker.vvsmarthomeservice.domain.TemperatureUnit;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Timestamp;

@Entity
@Data
public class PersistentSensorData {
    @Id
    @GeneratedValue
    private Long id;

    @Min(0)
    @Max(30)
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
