package de.nicolasschlecker.vv.smarthomeservice.controller;

import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.Sensor;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.SensorData;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.SensorDataPartial;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.SensorPartial;
import de.nicolasschlecker.vv.smarthomeservice.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sensors")
public class SensorController {

    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Sensor>> findAll() {
        return ResponseEntity.ok(sensorService.findAll());
    }

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Sensor> addSensor(@RequestBody SensorPartial sensor) {
        return ResponseEntity.ok(sensorService.create(sensor));
    }

    @GetMapping(value = "/{sensorId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Sensor> findSensorById(@PathVariable Long sensorId) {
        return ResponseEntity.ok(sensorService.find(sensorId));
    }

    @GetMapping(value = "/{sensorId}/data", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SensorData> findSensorDataBySensorId(@PathVariable Long sensorId) {
        return ResponseEntity.ok(sensorService.findData(sensorId));
    }

    @PutMapping(value = "/{sensorId}/data", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SensorData> updateSensorDataBySensorId(@PathVariable Long sensorId, @RequestBody SensorDataPartial sensorDataPartial) {
        return ResponseEntity.ok(sensorService.updateData(sensorId, sensorDataPartial));
    }

    @PutMapping(value = "/{sensorId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Sensor> updateSensor(@PathVariable Long sensorId, @RequestBody SensorPartial sensor) {
        return ResponseEntity.ok(sensorService.update(sensorId, sensor));
    }

    @DeleteMapping(value = "/{sensorId}")
    public ResponseEntity<Void> deleteSensorById(@PathVariable Long sensorId) {
        sensorService.delete(sensorId);
        return ResponseEntity.noContent().build();
    }
}