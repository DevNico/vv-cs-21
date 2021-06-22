package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import de.nicolasschlecker.vv.smarthomeservice.domain.rule.Rule;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensordata.SensorData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "sensor")
public class Sensor {
    @Id
    private Long id;

    private String name;
    private String location;

    @OneToOne()
    @JoinColumn(name = "sensor_data_id", referencedColumnName = "id")
    private SensorData sensorData;

    @OneToMany(mappedBy = "sensor")
    private List<Rule> rules;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private Timestamp deletedAt;
}
