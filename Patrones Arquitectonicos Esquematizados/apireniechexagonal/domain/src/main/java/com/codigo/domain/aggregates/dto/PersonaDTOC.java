package com.codigo.domain.aggregates.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaDTOC {
    private Long id;
    private String firstName;
    private String firstLastName;
    private String secondLastName;
    private String fullName;
    private String documentNumber;

}
