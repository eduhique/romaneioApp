package com.eduardopontes.romaneioapp.service;

import com.eduardopontes.romaneioapp.dto.ProductTypeDto;
import com.eduardopontes.romaneioapp.model.product.ProductType;

public interface ProductTypeService {
    ProductType save(ProductType productType);

    void updateForObj(ProductType productType, ProductTypeDto productType1);
}
