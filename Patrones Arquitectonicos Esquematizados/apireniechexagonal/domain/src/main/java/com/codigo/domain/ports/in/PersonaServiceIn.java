package com.codigo.domain.ports.in;

import com.codigo.domain.aggregates.dto.PersonaDTO;
import com.codigo.domain.aggregates.dto.PersonaDTOC;

public interface PersonaServiceIn {
        PersonaDTOC createPersonIn(String dni);
}
