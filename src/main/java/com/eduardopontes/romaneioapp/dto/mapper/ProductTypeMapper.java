package com.eduardopontes.romaneioapp.dto.mapper;

import com.eduardopontes.romaneioapp.dto.ProductTypeDto;
import com.eduardopontes.romaneioapp.model.product.ProductType;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductPrimitiveTypeMapper.class, ProductConversionTypeMapper.class})
public interface ProductTypeMapper {

    @Mapping(target = "id", ignore = true)
    ProductType toProductType(ProductTypeDto productTypeDto);

    @InheritInverseConfiguration
    ProductTypeDto fromProductType(ProductType productType);
}
