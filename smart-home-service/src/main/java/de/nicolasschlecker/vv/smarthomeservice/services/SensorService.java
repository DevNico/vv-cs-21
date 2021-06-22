package de.nicolasschlecker.vv.smarthomeservice.services;

import de.nicolasschlecker.vv.smarthomeservice.common.SensorMapper;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.SensorDto;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.SensorRequestDto;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensordata.SensorData;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensordata.SensorDataDto;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensordata.SensorDataRequestDto;
import de.nicolasschlecker.vv.smarthomeservice.repositories.SensorDataRepository;
import de.nicolasschlecker.vv.smarthomeservice.repositories.SensorRepository;
import de.nicolasschlecker.vv.smarthomeservice.services.exceptions.IdMismatchException;
import de.nicolasschlecker.vv.smarthomeservice.services.exceptions.SensorExistsException;
import de.nicolasschlecker.vv.smarthomeservice.services.exceptions.SensorNotFoundException;
import de.nicolasschlecker.vv.smarthomeservice.services.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SensorService {
    private final Validator validator;
    private final SensorMapper sensorMapper;

    private final SensorRepository sensorRepository;
    private final SensorDataRepository sensorDataRepository;

    @Autowired
    public SensorService(Validator validator, SensorMapper sensorMapper, SensorRepository sensorRepository, SensorDataRepository sensorDataRepository) {
        this.validator = validator;
        this.sensorMapper = sensorMapper;
        this.sensorRepository = sensorRepository;
        this.sensorDataRepository = sensorDataRepository;
    }

    public SensorDto create(SensorRequestDto partial) throws SensorExistsException, ValidationException {
        final var violations = new ArrayList<ConstraintViolation<?>>(validator.validate(partial));
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }

        if (sensorRepository.existsById(partial.getId())) {
            throw new SensorExistsException();
        }

        final var persistentSensorData = new SensorData();
        final var persistedSensorData = sensorDataRepository.save(persistentSensorData);

        final var persistentSensor = sensorMapper.sensorPartialToPersistentSensor(partial);
        persistentSensor.setSensorData(persistedSensorData);
        final var persistedSensor = sensorRepository.save(persistentSensor);
        return sensorMapper.sensorFromPersistentSensor(persistedSensor);
    }

    public SensorDataDto findData(Long sensorId) throws SensorNotFoundException {
        final var optionalPersistentSensor = sensorRepository.findById(sensorId);

        if (optionalPersistentSensor.isEmpty() || optionalPersistentSensor.get().getDeletedAt() != null) {
            throw new SensorNotFoundException();
        }

        return sensorMapper.sensorDataFromPersistentSensorData(optionalPersistentSensor.get().getSensorData());
    }

    public SensorDataDto updateData(Long sensorId, SensorDataRequestDto sensorDataRequestDto) throws SensorNotFoundException {
        final var violations = new ArrayList<ConstraintViolation<?>>(validator.validate(sensorDataRequestDto));
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }

        final var optionalPersistentSensor = sensorRepository.findById(sensorId);

        if (optionalPersistentSensor.isEmpty() || optionalPersistentSensor.get().getDeletedAt() != null) {
            throw new SensorNotFoundException();
        }

        final var persistentSensorData = optionalPersistentSensor.get().getSensorData();
        persistentSensorData.setCurrentValue(sensorDataRequestDto.getCurrentValue());
        persistentSensorData.setTimestamp(Timestamp.valueOf(sensorDataRequestDto.getTimestamp()));
        persistentSensorData.setTemperatureUnit(sensorDataRequestDto.getTemperatureUnit());

        final var persistedSensorData = sensorDataRepository.save(persistentSensorData);
        return sensorMapper.sensorDataFromPersistentSensorData(persistedSensorData);
    }

    public SensorDto update(Long id, SensorRequestDto sensor) throws IdMismatchException, SensorNotFoundException {
        if (!id.equals(sensor.getId())) {
            throw new IdMismatchException();
        }

        final var optionalPersistentSensor = sensorRepository.findById(id);

        if (optionalPersistentSensor.isEmpty() || optionalPersistentSensor.get().getDeletedAt() != null) {
            throw new SensorNotFoundException();
        }

        final var persistent = optionalPersistentSensor.get();
        persistent.setName(sensor.getName());
        persistent.setLocation(sensor.getLocation());
        final var persisted = sensorRepository.save(persistent);
        return sensorMapper.sensorFromPersistentSensor(persisted);
    }

    public List<SensorDto> findAll() {
        return StreamSupport
                .stream(sensorRepository.findAll().spliterator(), false)
                .filter(s -> s.getDeletedAt() == null)
                .map(sensorMapper::sensorFromPersistentSensor)
                .collect(Collectors.toList());
    }

    public SensorDto find(Long sensorId) throws SensorNotFoundException {
        final var optionalPersistentSensor = sensorRepository.findById(sensorId);

        if (optionalPersistentSensor.isEmpty() || optionalPersistentSensor.get().getDeletedAt() != null) {
            throw new SensorNotFoundException();
        }

        return sensorMapper.sensorFromPersistentSensor(optionalPersistentSensor.get());
    }

    public void delete(Long sensorId) throws SensorNotFoundException {
        final var optionalPersistentSensor = sensorRepository.findById(sensorId);

        if (optionalPersistentSensor.isEmpty() || optionalPersistentSensor.get().getDeletedAt() != null) {
            throw new SensorNotFoundException();
        }

        final var sensor = optionalPersistentSensor.get();
        sensor.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        sensorRepository.save(sensor);
    }
}
