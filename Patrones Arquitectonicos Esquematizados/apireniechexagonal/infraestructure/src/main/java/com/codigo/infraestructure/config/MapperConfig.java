package com.codigo.infraestructure.config;

import com.codigo.infraestructure.entity.PersonaEntity;
import com.codigo.infraestructure.response.ReniecResponse;
import com.codigo.infraestructure.response.ReniecResponseA;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.record.RecordModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

@Configuration
public class MapperConfig {

    @Bean(name = "defaultMapper")
    public ModelMapper defaultMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        mapper.registerModule(new RecordModule());
        return mapper;
    }

    @Bean(name = "reniecMapper")
    public ModelMapper reniecMapper(){
        ModelMapper mapper = new ModelMapper();
        //STRICT = Mapeo exacto entre nombre y tipo para que coincidan entre origne y destino
        //LOOSE = firtsName -> firts_name
        //Standard -> usan JavaBeans para emparejar los campos
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.createTypeMap(ReniecResponseA.class, PersonaEntity.class)
                .addMapping(ReniecResponseA::getFirst_name, (dest, v) -> dest.setFirstName((String) v))
                .addMapping(ReniecResponseA::getFirst_last_name, (dest, v) -> dest.setFirstLastName((String) v))
                .addMapping(ReniecResponseA::getSecond_last_name, (dest, v) -> dest.setSecondLastName((String) v))
                .addMapping(ReniecResponseA::getFull_name, (dest, v) -> dest.setFullName((String) v))
                .addMapping(ReniecResponseA::getDocument_number, (dest, v) -> dest.setDocumentNumber((String) v));

        return mapper;
    }
}
