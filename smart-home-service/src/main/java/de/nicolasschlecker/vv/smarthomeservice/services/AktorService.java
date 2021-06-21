package de.nicolasschlecker.vv.smarthomeservice.services;

import de.nicolasschlecker.vv.smarthomeservice.common.AktorMapper;
import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.Aktor;
import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.AktorPartial;
import de.nicolasschlecker.vv.smarthomeservice.repositories.AktorRepository;
import de.nicolasschlecker.vv.smarthomeservice.services.exceptions.AktorExistsException;
import de.nicolasschlecker.vv.smarthomeservice.services.exceptions.AktorNotFoundException;
import de.nicolasschlecker.vv.smarthomeservice.services.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AktorService {
    private final Validator validator;
    private final AktorMapper aktorMapper;
    private final AktorRepository aktorRepository;

    @Autowired
    public AktorService(Validator validator, AktorMapper aktorMapper, AktorRepository aktorRepository) {
        this.validator = validator;
        this.aktorMapper = aktorMapper;
        this.aktorRepository = aktorRepository;
    }

    public Aktor create(AktorPartial aktorPartial) {
        final var violations = new ArrayList<ConstraintViolation<?>>(validator.validate(aktorPartial));
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }

        final var persistent = aktorMapper.aktorPartialToPersistent(aktorPartial);
        final var persisted = aktorRepository.save(persistent);
        return aktorMapper.persistentToAktor(persisted);
    }

    public List<Aktor> findAll() {
        return StreamSupport
                .stream(aktorRepository.findAll().spliterator(), false)
                .filter(p -> p.getDeletedAt() == null)
                .map(aktorMapper::persistentToAktor)
                .collect(Collectors.toList());
    }

    public Aktor find(Long sensorId) throws AktorNotFoundException {
        final var optionalPersistentEntity = aktorRepository.findById(sensorId);

        if (optionalPersistentEntity.isEmpty()) {
            throw new AktorNotFoundException();
        }

        return aktorMapper.persistentToAktor(optionalPersistentEntity.get());
    }

}
