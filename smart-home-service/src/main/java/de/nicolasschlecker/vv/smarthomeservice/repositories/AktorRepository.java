package de.nicolasschlecker.vv.smarthomeservice.repositories;

import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.PersistentAktor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AktorRepository extends CrudRepository<PersistentAktor, Long> {
}
