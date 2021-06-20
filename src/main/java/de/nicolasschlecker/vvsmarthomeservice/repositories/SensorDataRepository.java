package de.nicolasschlecker.vvsmarthomeservice.repositories;

import de.nicolasschlecker.vvsmarthomeservice.domain.sensor.PersistentSensorData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorDataRepository extends CrudRepository<PersistentSensorData, Long> {
}