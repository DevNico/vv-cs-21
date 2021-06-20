package de.nicolasschlecker.vvsmarthomeservice.controller;

import de.nicolasschlecker.vvsmarthomeservice.domain.sensor.Sensor;
import de.nicolasschlecker.vvsmarthomeservice.services.SensorService;
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
        final var sensors = mService.findAll();
        return ResponseEntity.ok(sensors);
    }

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Sensor> addSensor(@RequestBody Sensor sensor) {
        final var created = mService.create(sensor);
        return created.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build());
    }

    @GetMapping(value = "/{sensorId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Sensor> findSensorById(@PathVariable Long sensorId) {
        final var sensor = mService.find(sensorId);
        return sensor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{sensorId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Sensor> updateSensor(@PathVariable Long sensorId, @RequestBody Sensor sensor) {
        final var updated = mService.update(sensorId, sensor);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{sensorId}")
    public ResponseEntity<Void> deleteSensorById(@PathVariable Long sensorId) {
        final var deleted = mService.delete(sensorId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}