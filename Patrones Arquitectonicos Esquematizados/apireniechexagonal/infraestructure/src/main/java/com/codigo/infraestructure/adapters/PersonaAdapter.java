package com.codigo.infraestructure.adapters;

import com.codigo.domain.aggregates.dto.PersonaDTO;
import com.codigo.domain.aggregates.dto.PersonaDTOC;
import com.codigo.domain.ports.out.PersonaServiceOut;
import com.codigo.infraestructure.entity.PersonaEntity;
import com.codigo.infraestructure.repository.PersonaRepository;
import com.codigo.infraestructure.response.ReniecResponse;
import com.codigo.infraestructure.response.ReniecResponseA;
import com.codigo.infraestructure.rest.ReniecClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonaAdapter implements PersonaServiceOut {

    private final ModelMapper defaultMapper;
    private final ModelMapper reniecMapper;
    private final ReniecClient reniecClient;
    private final PersonaRepository personaRepository;

    @Value("${reniec.token}")
    private String token;
    public PersonaAdapter(@Qualifier("defaultMapper") ModelMapper defaultMapper,
                          @Qualifier("reniecMapper") ModelMapper reniecMapper,
                          ReniecClient reniecClient,
                          PersonaRepository personaRepository) {
        this.defaultMapper = defaultMapper;
        this.reniecMapper = reniecMapper;
        this.reniecClient = reniecClient;
        this.personaRepository = personaRepository;
    }

    @Override
    public PersonaDTOC createPersonOut(String dni) {
        ReniecResponseA reniecResponse = executeReniec(dni);
        if(reniecResponse == null){
            throw new RuntimeException("Respuesta invalida de RENIEC");
        }
        PersonaEntity personaEntity = mapReniecToEntity(reniecResponse);
        return mapToPersonaDTO(personaRepository.save(personaEntity));
    }

    private ReniecResponseA executeReniec(String dni){
        String headerToken = "Bearer "+token;
        return Optional.ofNullable(reniecClient.getInforReniec(dni,headerToken))
                .orElseThrow(() -> new RuntimeException("Error al consultar Reniec"));
    }
    private PersonaDTOC mapToPersonaDTO(PersonaEntity personaEntity){
        return defaultMapper.map(personaEntity, PersonaDTOC.class);
    }
    private PersonaEntity mapReniecToEntity(ReniecResponseA reniecResponse){
        return reniecMapper.map(reniecResponse, PersonaEntity.class);
    }
}
