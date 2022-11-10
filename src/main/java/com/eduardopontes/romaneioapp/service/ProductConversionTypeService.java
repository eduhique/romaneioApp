package com.eduardopontes.romaneioapp.service;

import com.eduardopontes.romaneioapp.dto.ProductConversionTypeDto;
import com.eduardopontes.romaneioapp.model.product.ProductConversionType;
import com.eduardopontes.romaneioapp.model.product.ProductType;

import java.util.List;
import java.util.Set;

public interface ProductConversionTypeService {
    List<ProductConversionType> saveAll(ProductType productType, Set<ProductConversionType> productConversionTypeSet);

    void updateAll(ProductType productType, Set<ProductConversionTypeDto> productConversionTypes);
}
