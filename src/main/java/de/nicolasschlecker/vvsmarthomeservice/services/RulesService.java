package de.nicolasschlecker.vvsmarthomeservice.services;

import de.nicolasschlecker.vvsmarthomeservice.common.RuleMapper;
import de.nicolasschlecker.vvsmarthomeservice.domain.rule.PersistentRule;
import de.nicolasschlecker.vvsmarthomeservice.domain.rule.Rule;
import de.nicolasschlecker.vvsmarthomeservice.domain.rule.RulePartial;
import de.nicolasschlecker.vvsmarthomeservice.repositories.AktorRepository;
import de.nicolasschlecker.vvsmarthomeservice.repositories.RuleRepository;
import de.nicolasschlecker.vvsmarthomeservice.repositories.SensorRepository;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.AktorNotFoundException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.RuleNotFoundException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.SensorNotFoundException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RulesService {
    private final Validator validator;
    private final RuleMapper ruleMapper;

    private final RuleRepository ruleRepository;
    private final AktorRepository aktorRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public RulesService(Validator validator, RuleMapper ruleMapper, RuleRepository ruleRepository, AktorRepository aktorRepository, SensorRepository sensorRepository) {
        this.validator = validator;
        this.ruleMapper = ruleMapper;
        this.ruleRepository = ruleRepository;
        this.aktorRepository = aktorRepository;
        this.sensorRepository = sensorRepository;
    }

    public Rule create(RulePartial rulePartial) throws AktorNotFoundException, SensorNotFoundException, ValidationException {
        final var violations = new ArrayList<ConstraintViolation<?>>(validator.validate(rulePartial));
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }

        final var optionalPersistentAktor = aktorRepository.findById(rulePartial.getAktorId());
        if (optionalPersistentAktor.isEmpty()) {
            throw new AktorNotFoundException();
        }

        final var optionalPersistentSensor = sensorRepository.findById(rulePartial.getSensorId());
        if (optionalPersistentSensor.isEmpty()) {
            throw new SensorNotFoundException();
        }

        final var persistentEntity = new PersistentRule();
        persistentEntity.setName(rulePartial.getName());
        persistentEntity.setThreshold(rulePartial.getThreshold());
        persistentEntity.setAktor(optionalPersistentAktor.get());
        persistentEntity.setSensor(optionalPersistentSensor.get());
        return ruleMapper.fromPersistent(ruleRepository.save(persistentEntity));
    }

    public List<Rule> findAll() {
        return StreamSupport
                .stream(ruleRepository.findAll().spliterator(), false)
                .filter(p -> p.getDeletedAt() == null)
                .map(ruleMapper::fromPersistent)
                .collect(Collectors.toList());
    }

    public Rule findById(Long sensorId) throws RuleNotFoundException {
        final var optionalPersistentEntity = ruleRepository.findById(sensorId);

        if (optionalPersistentEntity.isEmpty()) {
            throw new RuleNotFoundException();
        }

        final var rule = optionalPersistentEntity.get();

        if (rule.getDeletedAt() != null) {
            throw new RuleNotFoundException();
        }

        return ruleMapper.fromPersistent(rule);
    }

    public Rule findByName(String name) throws RuleNotFoundException {
        final var optionalPersistentEntity = ruleRepository.findByName(name);

        if (optionalPersistentEntity.isEmpty()) {
            throw new RuleNotFoundException();
        }

        final var rule = optionalPersistentEntity.get();

        if (rule.getDeletedAt() != null) {
            throw new RuleNotFoundException();
        }

        return ruleMapper.fromPersistent(rule);
    }
}
