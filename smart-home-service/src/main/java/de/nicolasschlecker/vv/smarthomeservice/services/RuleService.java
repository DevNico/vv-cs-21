package de.nicolasschlecker.vv.smarthomeservice.services;

import de.nicolasschlecker.vv.smarthomeservice.common.RuleMapper;
import de.nicolasschlecker.vv.smarthomeservice.domain.rule.Rule;
import de.nicolasschlecker.vv.smarthomeservice.domain.rule.RuleDto;
import de.nicolasschlecker.vv.smarthomeservice.domain.rule.RuleRequestDto;
import de.nicolasschlecker.vv.smarthomeservice.repositories.AktorRepository;
import de.nicolasschlecker.vv.smarthomeservice.repositories.RuleRepository;
import de.nicolasschlecker.vv.smarthomeservice.repositories.SensorRepository;
import de.nicolasschlecker.vv.smarthomeservice.services.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RuleService {
    private final Validator validator;
    private final RuleMapper ruleMapper;

    private final RuleRepository ruleRepository;
    private final AktorRepository aktorRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public RuleService(Validator validator, RuleMapper ruleMapper, RuleRepository ruleRepository, AktorRepository aktorRepository, SensorRepository sensorRepository) {
        this.validator = validator;
        this.ruleMapper = ruleMapper;
        this.ruleRepository = ruleRepository;
        this.aktorRepository = aktorRepository;
        this.sensorRepository = sensorRepository;
    }

    public RuleDto create(RuleRequestDto ruleRequestDto) throws AktorNotFoundException, SensorNotFoundException, ValidationException {
        final var violations = new ArrayList<ConstraintViolation<?>>(validator.validate(ruleRequestDto));
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }


        final var optionalPersistentRule = ruleRepository.findByName(ruleRequestDto.getName());
        if (optionalPersistentRule.isPresent()) {
            throw new RuleExistsException();
        }

        final var optionalPersistentAktor = aktorRepository.findById(ruleRequestDto.getAktorId());
        if (optionalPersistentAktor.isEmpty()) {
            throw new AktorNotFoundException();
        }

        final var optionalPersistentSensor = sensorRepository.findById(ruleRequestDto.getSensorId());
        if (optionalPersistentSensor.isEmpty()) {
            throw new SensorNotFoundException();
        }

        final var persistentEntity = new Rule();
        persistentEntity.setName(ruleRequestDto.getName());
        persistentEntity.setThreshold(ruleRequestDto.getThreshold());
        persistentEntity.setAktor(optionalPersistentAktor.get());
        persistentEntity.setSensor(optionalPersistentSensor.get());
        return ruleMapper.fromPersistent(ruleRepository.save(persistentEntity));
    }

    public List<RuleDto> findAll() {
        return StreamSupport
                .stream(ruleRepository.findAll().spliterator(), false)
                .filter(p -> p.getDeletedAt() == null)
                .map(ruleMapper::fromPersistent)
                .collect(Collectors.toList());
    }

    public RuleDto findById(Long sensorId) throws RuleNotFoundException {
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

    public RuleDto findByName(String name) throws RuleNotFoundException {
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
