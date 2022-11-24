package com.eduardopontes.romaneioapp.service.impl;

import com.eduardopontes.romaneioapp.dto.ProductConversionTypeDto;
import com.eduardopontes.romaneioapp.dto.mapper.ProductConversionTypeMapper;
import com.eduardopontes.romaneioapp.model.product.ProductConversionType;
import com.eduardopontes.romaneioapp.model.product.ProductPrimitiveType;
import com.eduardopontes.romaneioapp.model.product.ProductType;
import com.eduardopontes.romaneioapp.repository.ProductConversionTypeRepository;
import com.eduardopontes.romaneioapp.service.ProductConversionTypeService;
import com.eduardopontes.romaneioapp.service.ProductPrimitiveTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductConversionTypeServiceImpl implements ProductConversionTypeService {

    private final ProductConversionTypeRepository productConversionTypeRepository;

    private final ProductPrimitiveTypeService primitiveTypeService;

    private final ProductConversionTypeMapper productConversionTypeMapper;

    public ProductConversionTypeServiceImpl(ProductConversionTypeRepository productConversionTypeRepository,
            ProductPrimitiveTypeService primitiveTypeService, ProductConversionTypeMapper productConversionTypeMapper) {
        this.productConversionTypeRepository = productConversionTypeRepository;
        this.primitiveTypeService = primitiveTypeService;
        this.productConversionTypeMapper = productConversionTypeMapper;
    }

    @Transactional
    @Override
    public List<ProductConversionType> saveAll(ProductType productType,
            Set<ProductConversionType> productConversionTypeSet) {
        productConversionTypeSet.forEach(productConversionType -> {
            productConversionType.setProductType(productType);
            ProductPrimitiveType productPrimitiveType =
                    primitiveTypeService.findByShortName(
                            productConversionType.getTargetProductPrimitiveType().getShortName());
            productConversionType.setTargetProductPrimitiveType(productPrimitiveType);
        });

        return productConversionTypeRepository.saveAll(productConversionTypeSet);
    }

    @Transactional
    @Override
    public void updateAll(ProductType productType, Set<ProductConversionTypeDto> productConversionTypes) {
        Set<ProductConversionType> productConversionTypeSet = productType.getProductConversionTypes();
        productConversionTypeRepository.deleteAll(productConversionTypeSet);
        saveAll(productType,
                productConversionTypes.stream()
                        .map(productConversionTypeMapper::toProductConversionType)
                        .collect(Collectors.toSet()));
    }
}
