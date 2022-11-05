package com.eduardopontes.romaneioapp.dto.mapper;

import com.eduardopontes.romaneioapp.dto.ProductPrimitiveTypeDto;
import com.eduardopontes.romaneioapp.model.product.ProductPrimitiveType;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductPrimitiveTypeMapper {

    @Mapping(target = "id", ignore = true)
    ProductPrimitiveType toProductPrimitiveType(ProductPrimitiveTypeDto productPrimitiveTypeDto);

    @InheritInverseConfiguration
    ProductPrimitiveTypeDto fromProductPrimitiveType(ProductPrimitiveType productPrimitiveType);
}
