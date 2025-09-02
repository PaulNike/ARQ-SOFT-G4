package com.codigo.util;

import com.codigo.domain.PersonaEntity;
import com.codigo.dto.PersonaResponse;
import com.codigo.dto.ReniecResponse;

public final class Mapper {

    private Mapper(){

    } // Evitar isntanciar

    public static PersonaResponse toDto(PersonaEntity e){
        if(e == null) return null;
        return new PersonaResponse(
                e.getId(), e.getFirstName(),e.getFirstLastName(),e.getSecondLastName(),e.getFullName(),e.getDocumentNumber()
        );
    }

    public static void updateFromReniec(PersonaEntity target,
                                        String firstName,
                                        String firstLastName,
                                        String secondLastName,
                                        String fullName,
                                        String documentNumber){
        target.setDocumentNumber(documentNumber);
        target.setFirstName(firstName);
        target.setFirstLastName(firstLastName);
        target.setSecondLastName(secondLastName);
        target.setFullName(fullName);
    }
}
