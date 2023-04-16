package com.dietrich.fiducial.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represent the entity for the name table
 */
@Entity
@Table(name = "name")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Name {

    public Name(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
}
