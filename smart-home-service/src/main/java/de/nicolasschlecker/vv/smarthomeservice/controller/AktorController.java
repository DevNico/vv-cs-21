package de.nicolasschlecker.vv.smarthomeservice.controller;

import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.Aktor;
import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.AktorPartial;
import de.nicolasschlecker.vv.smarthomeservice.services.AktorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/aktors")
public class AktorController {

    private final AktorService aktorService;

    @Autowired
    public AktorController(AktorService aktorService) {
        this.aktorService = aktorService;
    }

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<Aktor>> findAll() {
        return ResponseEntity.ok(aktorService.findAll());
    }


    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Aktor> addAktor(@RequestBody AktorPartial aktor) {
        return ResponseEntity.ok(aktorService.create(aktor));
    }

    @GetMapping(value = "/{aktorId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Aktor> findAktorById(@PathVariable Long aktorId) {
        return ResponseEntity.ok(aktorService.find(aktorId));
    }
}