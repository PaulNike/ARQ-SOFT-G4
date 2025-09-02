package com.codigo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository
        extends JpaRepository<PersonaEntity, Long> {
     Optional<PersonaEntity> findByDocumentNumber(String dni);
}
