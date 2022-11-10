package com.eduardopontes.romaneioapp.service.impl;

import com.eduardopontes.romaneioapp.dto.ProductTypeDto;
import com.eduardopontes.romaneioapp.model.product.ProductConversionType;
import com.eduardopontes.romaneioapp.model.product.ProductType;
import com.eduardopontes.romaneioapp.repository.ProductTypeRepository;
import com.eduardopontes.romaneioapp.service.ProductConversionTypeService;
import com.eduardopontes.romaneioapp.service.ProductTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    private final ProductConversionTypeService conversionTypeService;

    public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository,
            ProductConversionTypeService conversionTypeService) {
        this.productTypeRepository = productTypeRepository;
        this.conversionTypeService = conversionTypeService;
    }

    @Transactional
    @Override
    public ProductType save(ProductType productType) {
        Set<ProductConversionType> productConversionTypeSet = productType.getProductConversionTypes();

        productType.setProductConversionTypes(new HashSet<>());
        productTypeRepository.save(productType);
        conversionTypeService.saveAll(productType, productConversionTypeSet);

        return productType;
    }

    @Transactional
    @Override
    public void updateForObj(ProductType productType, ProductTypeDto productTypeDto) {
        productType.setShortName(productTypeDto.getShortName());
        productType.setLongName(productTypeDto.getLongName());
        productType.setFloat(productTypeDto.isFloat());
        conversionTypeService.updateAll(productType, productTypeDto.getProductConversionTypes());

        productTypeRepository.save(productType);

    }
}
