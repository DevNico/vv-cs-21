package de.nicolasschlecker.vvsmarthomeservice.common;

import de.nicolasschlecker.vvsmarthomeservice.domain.sensor.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = TimestampLocalDateTimeMapper.class)
public interface SensorMapper {
    Sensor sensorFromPersistentSensor(PersistentSensor persistentSensor);

    PersistentSensor sensorPartialToPersistentSensor(SensorPartial sensor);

    @Mapping(target = "sensorId", source = "sensor.id")
    SensorData sensorDataFromPersistentSensorData(PersistentSensorData persistentSensorData);
}
