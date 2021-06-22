package de.nicolasschlecker.vv.smarthomeservice.repositories;

import de.nicolasschlecker.vv.smarthomeservice.domain.sensordata.SensorData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorDataRepository extends CrudRepository<SensorData, Long> {
}