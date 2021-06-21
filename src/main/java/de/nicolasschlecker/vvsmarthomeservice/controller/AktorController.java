package de.nicolasschlecker.vvsmarthomeservice.controller;

import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.Aktor;
import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.AktorPartial;
import de.nicolasschlecker.vvsmarthomeservice.services.AktorService;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.AktorExistsException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.AktorNotFoundException;
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
    public ResponseEntity<Aktor> addAktor(@RequestBody AktorPartial aktor) {
        try {
            return ResponseEntity.ok(mService.create(aktor));
        } catch (AktorExistsException e) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
        }
    }

    @GetMapping(value = "/{aktorId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Aktor> findAktorById(@PathVariable Long aktorId) {
        try {
            return ResponseEntity.ok(mService.find(aktorId));
        } catch (AktorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}