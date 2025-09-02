package com.codigo.service;

import com.codigo.client.ReniecClient;
import com.codigo.domain.PersonaEntity;
import com.codigo.domain.PersonaRepository;
import com.codigo.dto.PersonaResponse;
import com.codigo.dto.ReniecResponse;
import com.codigo.util.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PersonaServiceImpl implements PersonaService{

    private final PersonaRepository personaRepository;
    private final ReniecClient reniecClient;

    @Value("${reniec.token}")
    private String token;

    public PersonaServiceImpl(PersonaRepository personaRepository, ReniecClient reniecClient) {
        this.personaRepository = personaRepository;
        this.reniecClient = reniecClient;
    }

    @Override
    public PersonaResponse bsucarPorDni(String dni) {
        //Validando el DNI Formato o vacio
        String normalizado = dni == null ? "" : dni.trim();
        if (!normalizado.matches("\\d{8}")){
            throw new IllegalArgumentException("DNI Invalido: " + dni);
        }
        //Llamos a BD y valdiamos que exista la eprsona
        return personaRepository.findByDocumentNumber(dni)
                .map(Mapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Persona no encontrada"));
    }

    @Override
    public PersonaResponse sincronizarPorDni(String dni) {
        //Validando el DNI Formato o vacio
        String normalizado = dni == null ? "" : dni.trim();
        if (!normalizado.matches("\\d{8}")){
            throw new IllegalArgumentException("DNI Invalido: " + dni);
        }

        //Llamando al servicio extenro
        ReniecResponse externo = reniecClient.getInfoReniec(dni,token);

        String dniFromProvider = externo.document_number();
        if(dniFromProvider == null || !dniFromProvider.matches("\\d{8}")){
            throw new IllegalArgumentException("RENIEC devolvio un documento invalido para el DNI " + dni);
        }

        PersonaEntity entity = personaRepository.findByDocumentNumber(dni).orElseGet(PersonaEntity::new);

        Mapper.updateFromReniec(entity, externo.first_name(), externo.first_last_name(),
                externo.second_last_name(),externo.full_name(), externo.document_number());

        //persistir
        try {
            return Mapper.toDto(personaRepository.save(entity));
        }catch (DataIntegrityViolationException e){
            return personaRepository.findByDocumentNumber(dniFromProvider)
                    .map(Mapper::toDto)
                    .orElseThrow(() -> e);
        }


        //code = 200
        //message = "ok"
        //data = data que recibes

        //code = 5000
        //message = "ERROR RENIEC DEVOLVIO UN DNI NO VLAIDO"
        //Exception = ""
        //data = {}

    }
}
