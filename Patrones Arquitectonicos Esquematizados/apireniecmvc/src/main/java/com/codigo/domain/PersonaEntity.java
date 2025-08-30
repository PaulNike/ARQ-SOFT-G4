package com.codigo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "persona")
@Getter
@Setter
public class PersonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 8)
    private String firstName;
    @Column(name = "first_last_name")
    private String firstLastName;
    @Column(name = "second_last_name")
    private String secondLastName;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "document_number")
    private String documentNumber;

}
