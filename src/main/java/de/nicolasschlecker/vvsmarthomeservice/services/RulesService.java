package de.nicolasschlecker.vvsmarthomeservice.services;

import de.nicolasschlecker.vvsmarthomeservice.domain.rule.PersistentRule;
import de.nicolasschlecker.vvsmarthomeservice.domain.rule.Rule;
import de.nicolasschlecker.vvsmarthomeservice.repositories.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RulesService {
    private final RuleRepository mRepository;

    @Autowired
    public RulesService(RuleRepository mRepository) {
        this.mRepository = mRepository;
    }

    public Optional<Rule> create(Rule entity) {
        if (mRepository.existsById(entity.getId())) {
            return Optional.empty();
        }

        final var persistentEntity = persistentEntityFromEntity(entity);
        return Optional.of(entityFromPersistentEntity(mRepository.save(persistentEntity)));
    }

    public List<Rule> findAll() {
        return StreamSupport
                .stream(mRepository.findAll().spliterator(), false)
                .filter(p -> p.getDeletedAt() != null)
                .map(this::entityFromPersistentEntity)
                .collect(Collectors.toList());
    }

    public Optional<Rule> findById(Long sensorId) {
        final var optionalPersistentEntity = mRepository.findById(sensorId);

        return optionalPersistentEntity.map(e -> {
            if (e.getDeletedAt() == null) {
                return entityFromPersistentEntity(e);
            }
            return null;
        });
    }

    public Optional<Rule> findByName(String name) {
        final var optionalPersistentEntity = mRepository.findByName(name);

        return optionalPersistentEntity.map(e -> {
            if (e.getDeletedAt() == null) {
                return entityFromPersistentEntity(e);
            }
            return null;
        });
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
