package de.nicolasschlecker.vvsmarthomeservice.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Rule {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Min(1)
    @Max(29)
    private int threshold;

    @OneToOne
    private Sensor sensor;

    @OneToOne
    private Aktor aktor;
}
