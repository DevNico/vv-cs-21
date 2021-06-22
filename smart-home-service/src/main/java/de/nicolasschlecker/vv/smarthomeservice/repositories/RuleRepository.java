package de.nicolasschlecker.vv.smarthomeservice.repositories;

import de.nicolasschlecker.vv.smarthomeservice.domain.rule.Rule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuleRepository extends CrudRepository<Rule, Long> {
    @Query("SELECT t FROM #{#entityName} t WHERE t.name = :name")
    Optional<Rule> findByName(@Param("name") String name);
}
