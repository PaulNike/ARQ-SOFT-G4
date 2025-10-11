package com.codigo.domain.ports.out;

import com.codigo.domain.aggregates.dto.PersonaDTO;
import com.codigo.domain.aggregates.dto.PersonaDTOC;

public interface PersonaServiceOut  {
    PersonaDTOC createPersonOut(String dni);
}
