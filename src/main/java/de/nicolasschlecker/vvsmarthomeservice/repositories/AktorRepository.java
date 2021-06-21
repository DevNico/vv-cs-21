package de.nicolasschlecker.vvsmarthomeservice.repositories;

import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.PersistentAktor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AktorRepository extends CrudRepository<PersistentAktor, Long> {
}
