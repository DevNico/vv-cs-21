package de.nicolasschlecker.vvsmarthomeservice.common;

import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.Aktor;
import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.AktorPartial;
import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.PersistentAktor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = TimestampLocalDateTimeMapper.class)
public interface AktorMapper {
    Aktor persistentToAktor(PersistentAktor persistentAktor);

    PersistentAktor aktorPartialToPersistent(AktorPartial aktorPartial);
}
