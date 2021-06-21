package de.nicolasschlecker.vv.smarthomeservice.controller;

import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.Sensor;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.SensorData;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.SensorDataPartial;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.SensorPartial;
import de.nicolasschlecker.vv.smarthomeservice.services.SensorService;
import de.nicolasschlecker.vv.smarthomeservice.services.exceptions.IdMismatchException;
import de.nicolasschlecker.vv.smarthomeservice.services.exceptions.SensorExistsException;
import de.nicolasschlecker.vv.smarthomeservice.services.exceptions.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try {
            return ResponseEntity.ok(sensorService.create(sensor));
        } catch (SensorExistsException e) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
        }
    }

    @GetMapping(value = "/{sensorId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Sensor> findSensorById(@PathVariable Long sensorId) {
        try {
            return ResponseEntity.ok(sensorService.find(sensorId));
        } catch (SensorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{sensorId}/data", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SensorData> findSensorDataBySensorId(@PathVariable Long sensorId) {
        try {
            return ResponseEntity.ok(sensorService.findData(sensorId));
        } catch (SensorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{sensorId}/data", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SensorData> updateSensorDataBySensorId(@PathVariable Long sensorId, @RequestBody SensorDataPartial sensorDataPartial) {
        try {
            return ResponseEntity.ok(sensorService.updateData(sensorId, sensorDataPartial));
        } catch (SensorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{sensorId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Sensor> updateSensor(@PathVariable Long sensorId, @RequestBody SensorPartial sensor) {
        try {
            return ResponseEntity.ok(sensorService.update(sensorId, sensor));
        } catch (IdMismatchException e) {
            return ResponseEntity.badRequest().build();
        } catch (SensorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{sensorId}")
    public ResponseEntity<Void> deleteSensorById(@PathVariable Long sensorId) {
        try {
            sensorService.delete(sensorId);
            return ResponseEntity.noContent().build();
        } catch (SensorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}