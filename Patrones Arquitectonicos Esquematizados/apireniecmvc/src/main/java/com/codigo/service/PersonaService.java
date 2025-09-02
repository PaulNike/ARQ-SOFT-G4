package com.codigo.service;

import com.codigo.dto.PersonaResponse;

public interface PersonaService {

    PersonaResponse bsucarPorDni(String dni);
    PersonaResponse sincronizarPorDni(String dni);
}
