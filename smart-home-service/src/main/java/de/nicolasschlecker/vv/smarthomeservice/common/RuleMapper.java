package de.nicolasschlecker.vv.smarthomeservice.common;

import de.nicolasschlecker.vv.smarthomeservice.domain.rule.Rule;
import de.nicolasschlecker.vv.smarthomeservice.domain.rule.RuleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = TimestampLocalDateTimeMapper.class)
public interface RuleMapper {
    @Mapping(target = "sensorId", source = "sensor.id")
    @Mapping(target = "aktorId", source = "aktor.id")
    RuleDto fromPersistent(Rule rule);
}