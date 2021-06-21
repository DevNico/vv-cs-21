package de.nicolasschlecker.vv.smarthomeservice.common;

import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.Aktor;
import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.AktorPartial;
import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.PersistentAktor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = TimestampLocalDateTimeMapper.class)
public interface AktorMapper {
    Aktor persistentToAktor(PersistentAktor persistentAktor);

    PersistentAktor aktorPartialToPersistent(AktorPartial aktorPartial);
}
