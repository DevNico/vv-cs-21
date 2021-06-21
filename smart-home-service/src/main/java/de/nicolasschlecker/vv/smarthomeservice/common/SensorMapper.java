package de.nicolasschlecker.vv.smarthomeservice.common;

import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.*;
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
