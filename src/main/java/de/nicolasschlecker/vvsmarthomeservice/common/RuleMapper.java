package de.nicolasschlecker.vvsmarthomeservice.common;

import de.nicolasschlecker.vvsmarthomeservice.domain.rule.PersistentRule;
import de.nicolasschlecker.vvsmarthomeservice.domain.rule.Rule;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = TimestampLocalDateTimeMapper.class)
public interface RuleMapper {
    Rule fromPersistent(PersistentRule persistentRule);
}