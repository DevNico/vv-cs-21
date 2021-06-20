package de.nicolasschlecker.vvsmarthomeservice.services;

import de.nicolasschlecker.vvsmarthomeservice.domain.sensor.PersistentSensor;
import de.nicolasschlecker.vvsmarthomeservice.domain.sensor.Sensor;
import de.nicolasschlecker.vvsmarthomeservice.repositories.SensorRepository;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.IdMismatchException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.SensorExistsException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SensorService {
    private final SensorRepository mRepository;

    @Autowired
    public SensorService(SensorRepository mRepository) {
        this.mRepository = mRepository;
    }

    public Sensor create(Sensor sensor) throws SensorExistsException {
        if (mRepository.existsById(sensor.getId())) {
            throw new SensorExistsException();
        }

        final var persistentSensor = persistentSensorFromSensor(sensor);
        return sensorFromPersistentSensor(mRepository.save(persistentSensor));
    }

    public Sensor update(Long id, Sensor sensor) throws IdMismatchException, SensorNotFoundException {
        if (!id.equals(sensor.getId())) {
            throw new IdMismatchException();
        }

        final var optionalPersistentSensor = mRepository.findById(id);

        if (optionalPersistentSensor.isEmpty() || optionalPersistentSensor.get().getDeletedAt() != null) {
            throw new SensorNotFoundException();
        }

        return sensorFromPersistentSensor(mRepository.save(optionalPersistentSensor.get()));
    }

    public List<Sensor> findAll() {
        return StreamSupport
                .stream(mRepository.findAll().spliterator(), false)
                .map(this::sensorFromPersistentSensor)
                .collect(Collectors.toList());
    }

    public Sensor find(Long sensorId) throws SensorNotFoundException {
        final var optionalPersistentSensor = mRepository.findById(sensorId);

        if (optionalPersistentSensor.isEmpty() || optionalPersistentSensor.get().getDeletedAt() != null) {
            throw new SensorNotFoundException();
        }

        return sensorFromPersistentSensor(optionalPersistentSensor.get());
    }

    public void delete(Long sensorId) throws SensorNotFoundException {
        final var optionalPersistentSensor = mRepository.findById(sensorId);

        if (optionalPersistentSensor.isEmpty() || optionalPersistentSensor.get().getDeletedAt() != null) {
            throw new SensorNotFoundException();
        }

        final var sensor = optionalPersistentSensor.get();
        sensor.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        mRepository.save(sensor);
    }

    private Sensor sensorFromPersistentSensor(PersistentSensor persistentSensor) {
        final var sensor = new Sensor();
        sensor.setId(persistentSensor.getId());
        sensor.setName(persistentSensor.getName());
        sensor.setLocation(persistentSensor.getLocation());
        sensor.setCreatedAt(persistentSensor.getCreatedAt().toLocalDateTime());
        sensor.setUpdatedAt(persistentSensor.getUpdatedAt().toLocalDateTime());
        return sensor;
    }

    private PersistentSensor persistentSensorFromSensor(Sensor sensor) {
        final var persistentSensor = new PersistentSensor();
        persistentSensor.setId(sensor.getId());
        persistentSensor.setName(sensor.getName());
        persistentSensor.setLocation(sensor.getLocation());
        persistentSensor.setCreatedAt(Timestamp.valueOf(sensor.getCreatedAt()));
        persistentSensor.setUpdatedAt(Timestamp.valueOf(sensor.getUpdatedAt()));
        return persistentSensor;
    }
}
