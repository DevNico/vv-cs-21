package de.nicolasschlecker.vvsmarthomeservice.controller;

import de.nicolasschlecker.vvsmarthomeservice.domain.rule.Rule;
import de.nicolasschlecker.vvsmarthomeservice.domain.rule.RulePartial;
import de.nicolasschlecker.vvsmarthomeservice.services.RulesService;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.AktorNotFoundException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.RuleNotFoundException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<Rule> addRule(@RequestBody RulePartial rule) {
        try {
            return ResponseEntity.ok(mService.create(rule));
        } catch (AktorNotFoundException | SensorNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/{ruleId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Rule> findRuleById(@PathVariable Long ruleId) {
        try {
            return ResponseEntity.ok(mService.findById(ruleId));
        } catch (RuleNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{ruleName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Rule> findRuleById(@PathVariable String ruleName) {
        try {
            return ResponseEntity.ok(mService.findByName(ruleName));
        } catch (RuleNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}