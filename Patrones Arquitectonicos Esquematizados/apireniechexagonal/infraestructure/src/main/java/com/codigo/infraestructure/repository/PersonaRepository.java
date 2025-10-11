package com.codigo.infraestructure.repository;

import com.codigo.infraestructure.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonaRepository extends
        JpaRepository<PersonaEntity, Long> {
}
