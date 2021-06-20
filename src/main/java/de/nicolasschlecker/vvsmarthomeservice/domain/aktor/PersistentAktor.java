package de.nicolasschlecker.vvsmarthomeservice.domain.aktor;

import de.nicolasschlecker.vvsmarthomeservice.domain.rule.PersistentRule;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name = "aktor")
public class PersistentAktor {
    @Id
    private Long id;

    private String name;
    private String location;
    private String serviceUrl;
    private ShutterStatus currentState;

    @OneToMany(mappedBy = "aktor")
    private List<PersistentRule> rules;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private Timestamp deletedAt;
}
