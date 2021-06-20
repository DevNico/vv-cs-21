package de.nicolasschlecker.vvsmarthomeservice.services;

import de.nicolasschlecker.vvsmarthomeservice.domain.sensor.PersistentSensor;
import de.nicolasschlecker.vvsmarthomeservice.domain.sensor.Sensor;
import de.nicolasschlecker.vvsmarthomeservice.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SensorService {
    private final SensorRepository mRepository;

    @Autowired
    public SensorService(SensorRepository mRepository) {
        this.mRepository = mRepository;
    }

    public Optional<Sensor> create(Sensor sensor) {
        if (mRepository.existsById(sensor.getId())) {
            return Optional.empty();
        }

        final var persistentSensor = persistentSensorFromSensor(sensor);
        return Optional.of(sensorFromPersistentSensor(mRepository.save(persistentSensor)));
    }

    public Optional<Sensor> update(Long id, Sensor sensor) {
        if (!id.equals(sensor.getId())) {
            return Optional.empty();
        }

        final var persistentSensor = mRepository.findById(id);
        return persistentSensor.map((pE) -> sensorFromPersistentSensor(mRepository.save(pE)));
    }

    public List<Sensor> findAll() {
        return StreamSupport
                .stream(mRepository.findAll().spliterator(), false)
                .map(this::sensorFromPersistentSensor)
                .collect(Collectors.toList());
    }

    public Optional<Sensor> find(Long sensorId) {
        final var sensorDao = mRepository.findById(sensorId);
        return sensorDao.map(this::sensorFromPersistentSensor);
    }

    public boolean delete(Long sensorId) {
        final var optionalSensor = mRepository.findById(sensorId);

        return optionalSensor.map((sensor) -> {
            sensor.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
            mRepository.save(sensor);
            return true;
        }).orElse(false);
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
