package com.eduardopontes.romaneioapp.service.impl;

import com.eduardopontes.romaneioapp.dto.ProductTypeDto;
import com.eduardopontes.romaneioapp.model.product.ProductConversionType;
import com.eduardopontes.romaneioapp.model.product.ProductPrimitiveType;
import com.eduardopontes.romaneioapp.model.product.ProductType;
import com.eduardopontes.romaneioapp.repository.ProductTypeRepository;
import com.eduardopontes.romaneioapp.service.ProductConversionTypeService;
import com.eduardopontes.romaneioapp.service.ProductPrimitiveTypeService;
import com.eduardopontes.romaneioapp.service.ProductTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    private final ProductPrimitiveTypeService primitiveTypeService;

    private final ProductConversionTypeService conversionTypeService;

    public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository,
            ProductConversionTypeService conversionTypeService, ProductPrimitiveTypeService primitiveTypeService) {
        this.productTypeRepository = productTypeRepository;
        this.conversionTypeService = conversionTypeService;
        this.primitiveTypeService = primitiveTypeService;
    }

    @Transactional
    @Override
    public ProductType save(ProductType productType) {

        ProductPrimitiveType primitiveType =
                primitiveTypeService.findByShortName(productType.getProductPrimitiveType().getShortName());

        Set<ProductConversionType> productConversionTypeSet = productType.getProductConversionTypes();

        productType.setProductPrimitiveType(primitiveType);
        productType.setProductConversionTypes(new HashSet<>());
        productTypeRepository.save(productType);
        conversionTypeService.saveAll(productType, productConversionTypeSet);

        return productType;
    }

    @Transactional
    @Override
    public void updateForObj(ProductType productType, ProductTypeDto productTypeDto) {
        ProductPrimitiveType primitiveType =
                primitiveTypeService.findByShortName(productTypeDto.getProductPrimitiveType().getShortName());

        productType.setProductPrimitiveType(primitiveType);

        conversionTypeService.updateAll(productType, productTypeDto.getProductConversionTypes());
        productTypeRepository.save(productType);

    }
}
