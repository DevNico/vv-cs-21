package de.nicolasschlecker.vv.smarthomeservice.common;

import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.Aktor;
import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.AktorDto;
import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.AktorRequestDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = TimestampLocalDateTimeMapper.class)
public interface AktorMapper {
    AktorDto persistentToAktor(Aktor aktor);

    Aktor aktorPartialToPersistent(AktorRequestDto aktorRequestDto);
}
