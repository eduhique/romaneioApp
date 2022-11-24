package com.eduardopontes.romaneioapp.service;

import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.ProductPrimitiveTypeDto;
import com.eduardopontes.romaneioapp.model.product.ProductPrimitiveType;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

public interface ProductPrimitiveTypeService {
    ProductPrimitiveType findByShortName(String shortName);

    ProductPrimitiveTypeDto save(ProductPrimitiveTypeDto productPrimitiveTypeDto);

    void update(Long id, ProductPrimitiveTypeDto productPrimitiveTypeDto);

    ProductPrimitiveTypeDto findById(Long id);

    PageDto<ProductPrimitiveTypeDto> findAll(Example<ProductPrimitiveType> filter, Integer page, Integer size,
            Sort.Order order);

    void delete(Long id);
}
