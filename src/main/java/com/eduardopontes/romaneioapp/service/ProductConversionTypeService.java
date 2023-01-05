package com.eduardopontes.romaneioapp.service;

import com.eduardopontes.romaneioapp.dto.ProductConversionTypeDto;
import com.eduardopontes.romaneioapp.model.product.ProductConversionType;
import com.eduardopontes.romaneioapp.model.product.ProductType;

import java.util.Set;

public interface ProductConversionTypeService {
    void saveAll(ProductType productType, Set<ProductConversionType> productConversionTypeSet);

    void updateAll(ProductType productType, Set<ProductConversionTypeDto> productConversionTypes);
}
