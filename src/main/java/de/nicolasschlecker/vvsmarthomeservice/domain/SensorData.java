package de.nicolasschlecker.vvsmarthomeservice.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SensorData {
    @Id
    @GeneratedValue
    private Long id;

    @Min(0)
    @Max(30)
    private int currentValue;

    private Timestamp timestamp;

    private TemperatureUnit temperatureUnit;

    @OneToOne
    private Sensor sensor;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
