package de.nicolasschlecker.vv.smarthomeservice.domain.aktor;

import de.nicolasschlecker.vv.smarthomeservice.domain.rule.Rule;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Table(name = "aktor")
public class Aktor {
    @Id
    private Long id;

    private String name;
    private String location;
    private String serviceUrl;
    private ShutterStatus currentState;

    @OneToMany(mappedBy = "aktor")
    private List<Rule> rules;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private Timestamp deletedAt;
}
