package de.nicolasschlecker.vvsmarthomeservice.services;

import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.Aktor;
import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.PersistentAktor;
import de.nicolasschlecker.vvsmarthomeservice.repositories.AktorRepository;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.AktorExistsException;
import de.nicolasschlecker.vvsmarthomeservice.services.exceptions.AktorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AktorService {
    private final AktorRepository mRepository;

    @Autowired
    public AktorService(AktorRepository mRepository) {
        this.mRepository = mRepository;
    }

    public Aktor create(Aktor entity) throws AktorExistsException {
        if (mRepository.existsById(entity.getId())) {
            throw new AktorExistsException();
        }

        final var persistentEntity = persistentEntityFromEntity(entity);
        return entityFromPersistentEntity(mRepository.save(persistentEntity));
    }

    public List<Aktor> findAll() {
        return StreamSupport
                .stream(mRepository.findAll().spliterator(), false)
                .filter(p -> p.getDeletedAt() != null)
                .map(this::entityFromPersistentEntity)
                .collect(Collectors.toList());
    }

    public Aktor find(Long sensorId) throws AktorNotFoundException {
        final var optionalPersistentEntity = mRepository.findById(sensorId);

        if (optionalPersistentEntity.isEmpty()) {
            throw new AktorNotFoundException();
        }

        return entityFromPersistentEntity(optionalPersistentEntity.get());

    }

    protected Aktor entityFromPersistentEntity(PersistentAktor persistentAktor) {
        final var aktor = new Aktor();
        aktor.setId(persistentAktor.getId());
        aktor.setName(persistentAktor.getName());
        aktor.setLocation(persistentAktor.getLocation());
        aktor.setServiceUrl(persistentAktor.getServiceUrl());
        aktor.setCreatedAt(persistentAktor.getCreatedAt().toLocalDateTime());
        aktor.setUpdatedAt(persistentAktor.getUpdatedAt().toLocalDateTime());
        return aktor;
    }

    protected PersistentAktor persistentEntityFromEntity(Aktor aktor) {
        final var persistentAktor = new PersistentAktor();
        persistentAktor.setId(aktor.getId());
        persistentAktor.setName(aktor.getName());
        persistentAktor.setLocation(aktor.getLocation());
        persistentAktor.setServiceUrl(aktor.getServiceUrl());
        persistentAktor.setCreatedAt(Timestamp.valueOf(aktor.getCreatedAt()));
        persistentAktor.setUpdatedAt(Timestamp.valueOf(aktor.getUpdatedAt()));
        return persistentAktor;
    }
}
