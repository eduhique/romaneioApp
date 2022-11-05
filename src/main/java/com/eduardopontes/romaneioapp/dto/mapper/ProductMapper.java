package com.eduardopontes.romaneioapp.dto.mapper;

import com.eduardopontes.romaneioapp.dto.ProductDto;
import com.eduardopontes.romaneioapp.model.product.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductTypeMapper.class})
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toProduct(ProductDto productDto);

    @InheritInverseConfiguration
    ProductDto fromProduct(Product product);
}
