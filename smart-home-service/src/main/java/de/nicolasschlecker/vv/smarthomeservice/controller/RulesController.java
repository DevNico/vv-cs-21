package de.nicolasschlecker.vv.smarthomeservice.controller;

import de.nicolasschlecker.vv.smarthomeservice.domain.rule.RuleDto;
import de.nicolasschlecker.vv.smarthomeservice.domain.rule.RuleRequestDto;
import de.nicolasschlecker.vv.smarthomeservice.services.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rules")
public class RulesController {

    private final RuleService mService;

    @Autowired
    public RulesController(RuleService mService) {
        this.mService = mService;
    }

    @GetMapping(value = {"", "/"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<RuleDto>> findAll() {
        final var sensors = mService.findAll();
        return ResponseEntity.ok(sensors);
    }

    @PostMapping(value = {"", "/"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RuleDto> addRule(@RequestBody RuleRequestDto rule) {
        return ResponseEntity.ok(mService.create(rule));
    }

    @GetMapping(value = "/{ruleId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RuleDto> findRuleById(@PathVariable String ruleId) {
        try {
            return ResponseEntity.ok(mService.findById(Long.parseLong(ruleId)));
        } catch (NumberFormatException e) {
            return ResponseEntity.ok(mService.findByName(ruleId));
        }

    }
}