package de.nicolasschlecker.vvsmarthomeservice.service;

import de.nicolasschlecker.vvsmarthomeservice.domain.Sensor;
import de.nicolasschlecker.vvsmarthomeservice.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorService {
    @Autowired
    private SensorRepository mRepository;

    public Sensor addSensor(Sensor sensor) {
        return mRepository.save(sensor);
    }

    public Sensor findSensorById(Long sensorId) {
        return mRepository.findById(sensorId).orElse(null);
    }

    public void deleteSensor(Long sensorId) {
        mRepository.deleteById(sensorId);
    }
}
