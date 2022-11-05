package com.eduardopontes.romaneioapp.dto.mapper;

import com.eduardopontes.romaneioapp.dto.ProductConversionTypeDto;
import com.eduardopontes.romaneioapp.model.product.ProductConversionType;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductPrimitiveTypeMapper.class})
public interface ProductConversionTypeMapper {


    @Mapping(target = "productType", ignore = true)
    @Mapping(target = "id", ignore = true)
    ProductConversionType toProductConversionType(ProductConversionTypeDto productConversionTypeDto);

    @InheritInverseConfiguration
    ProductConversionTypeDto fromProductConversionType(ProductConversionType productConversionType);
}
