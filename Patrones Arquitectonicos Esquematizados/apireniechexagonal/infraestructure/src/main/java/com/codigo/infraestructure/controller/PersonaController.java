package com.codigo.infraestructure.controller;

import com.codigo.domain.aggregates.dto.PersonaDTO;
import com.codigo.domain.aggregates.dto.PersonaDTOC;
import com.codigo.domain.ports.in.PersonaServiceIn;
import org.hibernate.annotations.processing.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private final PersonaServiceIn personaServiceIn;

    public PersonaController(PersonaServiceIn personaServiceIn) {
        this.personaServiceIn = personaServiceIn;
    }

    @PostMapping("/{dni}/sync")
    public ResponseEntity<PersonaDTOC> sincronizar(@PathVariable
                                                       String dni){
        var dto = personaServiceIn.createPersonIn(dni);
        var location = URI.create("/api/personas/"+dto.getDocumentNumber());
        return ResponseEntity.ok()
                .location(location)
                .body(dto);

    }
}
