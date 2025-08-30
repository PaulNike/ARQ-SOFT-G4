package com.codigo.dto;

public record PersonaResponse(
        Long id,
        String firstName,
        String firstLastName,
        String secondLastName,
        String fullName,
        String documentNumber
) {
}
