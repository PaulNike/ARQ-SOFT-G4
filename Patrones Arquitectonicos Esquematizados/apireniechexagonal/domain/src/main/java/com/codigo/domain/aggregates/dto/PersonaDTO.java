package com.codigo.domain.aggregates.dto;


public record PersonaDTO(Long id,
                         String firstName,
                         String firstLastName,
                         String secondLastName,
                         String fullName,
                         String documentNumber) {
}

