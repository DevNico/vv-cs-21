package de.nicolasschlecker.vvsmarthomeservice.controller;

import de.nicolasschlecker.vvsmarthomeservice.domain.sensor.Sensor;
import de.nicolasschlecker.vvsmarthomeservice.services.SensorService;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.IdMismatchException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.SensorExistsException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensors")
public class SensorController {

    private final SensorService mService;

    @Autowired
    public SensorController(SensorService mService) {
        this.mService = mService;
    }

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<Sensor>> findAll() {
        return ResponseEntity.ok(mService.findAll());
    }

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Sensor> addSensor(@RequestBody Sensor sensor) {
        try {
            return ResponseEntity.ok(mService.create(sensor));
        } catch (SensorExistsException e) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
        }
    }

    @GetMapping(value = "/{sensorId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Sensor> findSensorById(@PathVariable Long sensorId) {
        try {
            return ResponseEntity.ok(mService.find(sensorId));
        } catch (SensorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{sensorId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Sensor> updateSensor(@PathVariable Long sensorId, @RequestBody Sensor sensor) {
        try {
            return ResponseEntity.ok(mService.update(sensorId, sensor));
        } catch (IdMismatchException e) {
            return ResponseEntity.badRequest().build();
        } catch (SensorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{sensorId}")
    public ResponseEntity<Void> deleteSensorById(@PathVariable Long sensorId) {
        try {
            mService.delete(sensorId);
            return ResponseEntity.noContent().build();
        } catch (SensorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}