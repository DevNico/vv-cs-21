package de.nicolasschlecker.vv.smarthomeservice.controller;

import de.nicolasschlecker.vv.smarthomeservice.domain.rule.Rule;
import de.nicolasschlecker.vv.smarthomeservice.domain.rule.RulePartial;
import de.nicolasschlecker.vv.smarthomeservice.services.RulesService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = {"", "/"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<Rule>> findAll() {
        final var sensors = mService.findAll();
        return ResponseEntity.ok(sensors);
    }

    @PostMapping(value = {"", "/"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Rule> addRule(@RequestBody RulePartial rule) {
        return ResponseEntity.ok(mService.create(rule));
    }

    @GetMapping(value = "/{ruleId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Rule> findRuleById(@PathVariable Long ruleId) {
        return ResponseEntity.ok(mService.findById(ruleId));
    }

    @GetMapping(value = "/{ruleName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Rule> findRuleById(@PathVariable String ruleName) {
        return ResponseEntity.ok(mService.findByName(ruleName));
    }
}