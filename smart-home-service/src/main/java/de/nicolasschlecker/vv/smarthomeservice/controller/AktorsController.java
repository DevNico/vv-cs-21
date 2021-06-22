package de.nicolasschlecker.vv.smarthomeservice.controller;

import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.AktorDto;
import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.AktorRequestDto;
import de.nicolasschlecker.vv.smarthomeservice.services.AktorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/aktors")
public class AktorsController {

    private final AktorService aktorService;

    @Autowired
    public AktorsController(AktorService aktorService) {
        this.aktorService = aktorService;
    }

    @GetMapping(value = {"", "/"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<AktorDto>> findAll() {
        return ResponseEntity.ok(aktorService.findAll());
    }


    @PostMapping(value = {"", "/"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AktorDto> addAktor(@RequestBody AktorRequestDto aktor) {
        return ResponseEntity.ok(aktorService.create(aktor));
    }

    @GetMapping(value = "/{aktorId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AktorDto> findAktorById(@PathVariable Long aktorId) {
        return ResponseEntity.ok(aktorService.find(aktorId));
    }
}