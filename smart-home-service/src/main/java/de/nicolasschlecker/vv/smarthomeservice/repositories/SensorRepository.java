package de.nicolasschlecker.vv.smarthomeservice.repositories;

import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.PersistentSensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends CrudRepository<PersistentSensor, Long> {
}
