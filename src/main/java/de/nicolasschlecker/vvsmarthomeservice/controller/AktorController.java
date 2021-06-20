package de.nicolasschlecker.vvsmarthomeservice.controller;

import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.Aktor;
import de.nicolasschlecker.vvsmarthomeservice.services.AktorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/aktors")
public class AktorController {

    private final AktorService mService;

    @Autowired
    public AktorController(AktorService mService) {
        this.mService = mService;
    }

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<Aktor>> findAll() {
        final var sensors = mService.findAll();
        return ResponseEntity.ok(sensors);
    }

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Aktor> addAktor(@RequestBody Aktor aktor) {
        final var created = mService.create(aktor);
        return created.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build());
    }

    @GetMapping(value = "/{aktorId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Aktor> findAktorById(@PathVariable Long aktorId) {
        final var aktor = mService.find(aktorId);

        return aktor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}