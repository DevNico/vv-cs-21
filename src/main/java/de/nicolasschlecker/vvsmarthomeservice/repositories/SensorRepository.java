package de.nicolasschlecker.vvsmarthomeservice.repositories;

import de.nicolasschlecker.vvsmarthomeservice.domain.Sensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, Long> {
}
