package com.codigo.controller;

import com.codigo.dto.PersonaResponse;
import com.codigo.service.PersonaService;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @PostMapping("/{dni}/sync")
    public ResponseEntity<PersonaResponse> sincronizar(@PathVariable
                                                       @Pattern(regexp = "\\d{8}", message = "DNI INVALIDO!!!!")
                                                       String dni){
        var dto = personaService.sincronizarPorDni(dni);
        var location = URI.create("/api/personas/"+dto.documentNumber());
        return ResponseEntity.ok()
                .location(location)
                .body(dto);

    }
}
