package de.nicolasschlecker.vvsmarthomeservice.services;

import de.nicolasschlecker.vvsmarthomeservice.common.SensorMapper;
import de.nicolasschlecker.vvsmarthomeservice.domain.sensor.*;
import de.nicolasschlecker.vvsmarthomeservice.repositories.SensorDataRepository;
import de.nicolasschlecker.vvsmarthomeservice.repositories.SensorRepository;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.IdMismatchException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.SensorExistsException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.SensorNotFoundException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.ValidationException;
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

    public Sensor create(SensorPartial partial) throws SensorExistsException, ValidationException {
        if (sensorRepository.existsById(partial.getId())) {
            throw new SensorExistsException();
        }

        final var violations = new ArrayList<ConstraintViolation<?>>(validator.validate(partial));
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }

        final var persistentSensorData = new PersistentSensorData();
        final var persistedSensorData = sensorDataRepository.save(persistentSensorData);

        final var persistentSensor = sensorMapper.sensorPartialToPersistentSensor(partial);
        persistentSensor.setSensorData(persistedSensorData);
        final var persistedSensor = sensorRepository.save(persistentSensor);
        return sensorMapper.sensorFromPersistentSensor(persistedSensor);
    }

    public SensorData findData(Long sensorId) throws SensorNotFoundException {
        final var optionalPersistentSensor = sensorRepository.findById(sensorId);

        if (optionalPersistentSensor.isEmpty() || optionalPersistentSensor.get().getDeletedAt() != null) {
            throw new SensorNotFoundException();
        }

        return sensorMapper.sensorDataFromPersistentSensorData(optionalPersistentSensor.get().getSensorData());
    }

    public SensorData updateData(Long sensorId, SensorDataPartial sensorDataPartial) throws SensorNotFoundException {
        final var optionalPersistentSensor = sensorRepository.findById(sensorId);

        if (optionalPersistentSensor.isEmpty() || optionalPersistentSensor.get().getDeletedAt() != null) {
            throw new SensorNotFoundException();
        }
        
        final var violations = new ArrayList<ConstraintViolation<?>>(validator.validate(sensorDataPartial));
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }

        final var persistentSensorData = optionalPersistentSensor.get().getSensorData();
        persistentSensorData.setCurrentValue(sensorDataPartial.getCurrentValue());
        persistentSensorData.setTimestamp(Timestamp.valueOf(sensorDataPartial.getTimestamp()));
        persistentSensorData.setTemperatureUnit(sensorDataPartial.getTemperatureUnit());

        final var persistedSensorData = sensorDataRepository.save(persistentSensorData);
        return sensorMapper.sensorDataFromPersistentSensorData(persistedSensorData);
    }

    public Sensor update(Long id, SensorPartial sensor) throws IdMismatchException, SensorNotFoundException {
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

    public List<Sensor> findAll() {
        return StreamSupport
                .stream(sensorRepository.findAll().spliterator(), false)
                .filter(s -> s.getDeletedAt() == null)
                .map(sensorMapper::sensorFromPersistentSensor)
                .collect(Collectors.toList());
    }

    public Sensor find(Long sensorId) throws SensorNotFoundException {
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
