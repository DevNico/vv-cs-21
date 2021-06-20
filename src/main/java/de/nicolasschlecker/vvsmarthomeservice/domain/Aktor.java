package de.nicolasschlecker.vvsmarthomeservice.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Aktor {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String location;

    private String serviceUrl;

    private String currentState;

    @OneToMany
    private List<Rule> rules;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
