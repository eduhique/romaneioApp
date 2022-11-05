package com.eduardopontes.romaneioapp.dto.mapper;

import com.eduardopontes.romaneioapp.dto.ClientDto;
import com.eduardopontes.romaneioapp.model.client.Client;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    Client toClient(ClientDto clientDto);

    @InheritInverseConfiguration
    ClientDto fromClient(Client client);
}
