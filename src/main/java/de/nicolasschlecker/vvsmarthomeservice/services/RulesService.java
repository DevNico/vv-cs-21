package de.nicolasschlecker.vvsmarthomeservice.services;

import de.nicolasschlecker.vvsmarthomeservice.domain.rule.PersistentRule;
import de.nicolasschlecker.vvsmarthomeservice.domain.rule.Rule;
import de.nicolasschlecker.vvsmarthomeservice.repositories.AktorRepository;
import de.nicolasschlecker.vvsmarthomeservice.repositories.RuleRepository;
import de.nicolasschlecker.vvsmarthomeservice.repositories.SensorRepository;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.AktorNotFoundException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.RuleExistsException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.RuleNotFoundException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RulesService {
    private final RuleRepository ruleRepository;
    private final AktorRepository aktorRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public RulesService(RuleRepository ruleRepository, AktorRepository aktorRepository, SensorRepository sensorRepository) {
        this.ruleRepository = ruleRepository;
        this.aktorRepository = aktorRepository;
        this.sensorRepository = sensorRepository;
    }

    public Rule create(Rule entity) throws RuleExistsException, AktorNotFoundException, SensorNotFoundException {
        if (ruleRepository.existsById(entity.getId())) {
            throw new RuleExistsException();
        }

        if(!aktorRepository.existsById(entity.getAktorId())) {
            throw new AktorNotFoundException();
        }

        if(!sensorRepository.existsById(entity.getSensorId())) {
            throw new SensorNotFoundException();
        }

        final var persistentEntity = persistentEntityFromEntity(entity);
        return entityFromPersistentEntity(ruleRepository.save(persistentEntity));
    }

    public List<Rule> findAll() {
        return StreamSupport
                .stream(ruleRepository.findAll().spliterator(), false)
                .filter(p -> p.getDeletedAt() != null)
                .map(this::entityFromPersistentEntity)
                .collect(Collectors.toList());
    }

    public Rule findById(Long sensorId) throws RuleNotFoundException {
        final var optionalPersistentEntity = ruleRepository.findById(sensorId);

        if(optionalPersistentEntity.isEmpty()) {
            throw new RuleNotFoundException();
        }

        final var rule = optionalPersistentEntity.get();

        if (rule.getDeletedAt() != null) {
            throw new RuleNotFoundException();
        }

        return entityFromPersistentEntity(rule);
    }

    public Rule findByName(String name) throws RuleNotFoundException {
        final var optionalPersistentEntity = ruleRepository.findByName(name);

        if(optionalPersistentEntity.isEmpty()) {
            throw new RuleNotFoundException();
        }

        final var rule = optionalPersistentEntity.get();

        if (rule.getDeletedAt() != null) {
            throw new RuleNotFoundException();
        }

        return entityFromPersistentEntity(rule);
    }

    protected Rule entityFromPersistentEntity(PersistentRule e) {
        final var rule = new Rule();
        rule.setId(e.getId());
        rule.setName(e.getName());
        rule.setThreshold(e.getThreshold());
        rule.setCreatedAt(e.getCreatedAt().toLocalDateTime());
        rule.setUpdatedAt(e.getUpdatedAt().toLocalDateTime());
        return rule;
    }

    protected PersistentRule persistentEntityFromEntity(Rule e) {
        final var persistentRule = new PersistentRule();
        persistentRule.setId(e.getId());
        persistentRule.setName(e.getName());
        persistentRule.setThreshold(e.getThreshold());
        persistentRule.setCreatedAt(Timestamp.valueOf(e.getCreatedAt()));
        persistentRule.setUpdatedAt(Timestamp.valueOf(e.getUpdatedAt()));
        return persistentRule;
    }
}
