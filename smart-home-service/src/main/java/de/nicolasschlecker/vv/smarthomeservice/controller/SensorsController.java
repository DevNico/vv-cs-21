package de.nicolasschlecker.vv.smarthomeservice.controller;

import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.SensorDto;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.SensorRequestDto;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensordata.SensorDataDto;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensordata.SensorDataRequestDto;
import de.nicolasschlecker.vv.smarthomeservice.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sensors")
public class SensorsController {

    private final SensorService sensorService;

    @Autowired
    public SensorsController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping(value = {"", "/"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SensorDto>> findAll() {
        return ResponseEntity.ok(sensorService.findAll());
    }

    @PostMapping(value = {"", "/"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SensorDto> addSensor(@RequestBody SensorRequestDto sensor) {
        return ResponseEntity.ok(sensorService.create(sensor));
    }

    @GetMapping(value = "/{sensorId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SensorDto> findSensorById(@PathVariable Long sensorId) {
        return ResponseEntity.ok(sensorService.find(sensorId));
    }

    @GetMapping(value = "/{sensorId}/data", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SensorDataDto> findSensorDataBySensorId(@PathVariable Long sensorId) {
        return ResponseEntity.ok(sensorService.findData(sensorId));
    }

    @PostMapping(value = "/{sensorId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SensorDataDto> updateSensorDataBySensorId(@PathVariable Long sensorId, @RequestBody SensorDataRequestDto sensorDataRequestDto) {
        return ResponseEntity.ok(sensorService.updateData(sensorId, sensorDataRequestDto));
    }

    @PutMapping(value = "/{sensorId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SensorDto> updateSensor(@PathVariable Long sensorId, @RequestBody SensorRequestDto sensor) {
        return ResponseEntity.ok(sensorService.update(sensorId, sensor));
    }

    @DeleteMapping(value = "/{sensorId}")
    public ResponseEntity<Void> deleteSensorById(@PathVariable Long sensorId) {
        sensorService.delete(sensorId);
        return ResponseEntity.noContent().build();
    }
}