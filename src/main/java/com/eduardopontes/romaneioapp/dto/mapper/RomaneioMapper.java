package com.eduardopontes.romaneioapp.dto.mapper;

import com.eduardopontes.romaneioapp.dto.RomaneioDto;
import com.eduardopontes.romaneioapp.model.romaneio.Romaneio;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RomaneioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "status", ignore = true)
    Romaneio toRomaneio(RomaneioDto romaneioDto);

    @InheritInverseConfiguration
    @Mapping(target = "orders", ignore = true)
    RomaneioDto fromRomaneio(Romaneio romaneio);
}
