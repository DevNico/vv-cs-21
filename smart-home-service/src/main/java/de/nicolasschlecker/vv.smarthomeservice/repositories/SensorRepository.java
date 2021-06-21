package de.nicolasschlecker.vvsmarthomeservice.repositories;

import de.nicolasschlecker.vvsmarthomeservice.domain.sensor.PersistentSensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends CrudRepository<PersistentSensor, Long> {
}
