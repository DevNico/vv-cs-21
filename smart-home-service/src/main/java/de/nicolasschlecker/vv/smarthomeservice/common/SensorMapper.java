package de.nicolasschlecker.vv.smarthomeservice.common;

import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.Sensor;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.SensorDto;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.SensorRequestDto;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensordata.SensorData;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensordata.SensorDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = TimestampLocalDateTimeMapper.class)
public interface SensorMapper {
    SensorDto sensorFromPersistentSensor(Sensor sensor);

    Sensor sensorPartialToPersistentSensor(SensorRequestDto sensor);

    @Mapping(target = "sensorId", source = "sensor.id")
    SensorDataDto sensorDataFromPersistentSensorData(SensorData sensorData);
}
