package de.nicolasschlecker.vvsmarthomeservice.controller;

import de.nicolasschlecker.vvsmarthomeservice.domain.Sensor;
import de.nicolasschlecker.vvsmarthomeservice.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensors")
public class SensorController {

    @Autowired
    private SensorService mService;

    @PostMapping(value = "/")
    public Sensor addSensor(@RequestBody Sensor sensor) {
        return mService.addSensor(sensor);
    }

    @GetMapping(value = "/{sensorId}")
    public Sensor findSensorById(@PathVariable Long sensorId) {
        return mService.findSensorById(sensorId);
    }

    @DeleteMapping(value = "/{sensorId}")
    public void deleteSensor(@PathVariable Long sensorId) {
        mService.deleteSensor(sensorId);
    }
}