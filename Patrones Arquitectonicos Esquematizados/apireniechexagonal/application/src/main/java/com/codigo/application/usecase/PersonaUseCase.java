package com.codigo.application.usecase;

import com.codigo.domain.aggregates.dto.PersonaDTO;
import com.codigo.domain.aggregates.dto.PersonaDTOC;
import com.codigo.domain.ports.in.PersonaServiceIn;
import com.codigo.domain.ports.out.PersonaServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonaUseCase implements PersonaServiceIn {

    private final PersonaServiceOut personaServiceOut;

    @Override
    public PersonaDTOC createPersonIn(String dni) {
        return personaServiceOut.createPersonOut(dni);
    }
}
