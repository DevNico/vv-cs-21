package de.nicolasschlecker.vvsmarthomeservice.domain.rule;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class PersistentRuleId implements Serializable {
    private Long id;
    private String name;
}
