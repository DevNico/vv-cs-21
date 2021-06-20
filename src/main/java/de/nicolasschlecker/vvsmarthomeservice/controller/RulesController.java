package de.nicolasschlecker.vvsmarthomeservice.controller;

import de.nicolasschlecker.vvsmarthomeservice.domain.rule.Rule;
import de.nicolasschlecker.vvsmarthomeservice.services.RulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rules")
public class RulesController {

    private final RulesService mService;

    @Autowired
    public RulesController(RulesService mService) {
        this.mService = mService;
    }

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<Rule>> findAll() {
        final var sensors = mService.findAll();
        return ResponseEntity.ok(sensors);
    }

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Rule> addRule(@RequestBody Rule rule) {
        final var created = mService.create(rule);
        return created.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build());
    }

    @GetMapping(value = "/{ruleId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Rule> findRuleById(@PathVariable Long ruleId) {
        final var rule = mService.findById(ruleId);

        return rule.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}