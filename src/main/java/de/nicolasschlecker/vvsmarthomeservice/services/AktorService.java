package de.nicolasschlecker.vvsmarthomeservice.services;

import de.nicolasschlecker.vvsmarthomeservice.common.AktorMapper;
import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.Aktor;
import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.AktorPartial;
import de.nicolasschlecker.vvsmarthomeservice.repositories.AktorRepository;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.AktorExistsException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.AktorNotFoundException;
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

    public Aktor create(AktorPartial aktorPartial) throws AktorExistsException {
        if (aktorRepository.existsById(aktorPartial.getId())) {
            throw new AktorExistsException();
        }

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
