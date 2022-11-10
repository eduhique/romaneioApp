package com.eduardopontes.romaneioapp.service.impl;

import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.ProductPrimitiveTypeDto;
import com.eduardopontes.romaneioapp.dto.mapper.ProductPrimitiveTypeMapper;
import com.eduardopontes.romaneioapp.model.product.ProductPrimitiveType;
import com.eduardopontes.romaneioapp.repository.ProductPrimitiveTypeRepository;
import com.eduardopontes.romaneioapp.service.ProductPrimitiveTypeService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Service
public class ProductPrimitiveTypeServiceImpl implements ProductPrimitiveTypeService {

    private final ProductPrimitiveTypeRepository productPrimitiveTypeRepository;

    private final ProductPrimitiveTypeMapper productPrimitiveTypeMapper;

    public ProductPrimitiveTypeServiceImpl(ProductPrimitiveTypeRepository productPrimitiveTypeRepository,
            ProductPrimitiveTypeMapper productPrimitiveTypeMapper) {
        this.productPrimitiveTypeRepository = productPrimitiveTypeRepository;
        this.productPrimitiveTypeMapper = productPrimitiveTypeMapper;
    }

    @Override
    @Transactional
    public ProductPrimitiveType saveOrGetByShortName(ProductPrimitiveType targetProductPrimitiveType) {
        return productPrimitiveTypeRepository.findByShortName(targetProductPrimitiveType.getShortName())
                .orElse(productPrimitiveTypeRepository.save(targetProductPrimitiveType));
    }

    @Transactional
    @Override
    public ProductPrimitiveTypeDto save(ProductPrimitiveTypeDto productPrimitiveTypeDto) {
        ProductPrimitiveType primitiveType = productPrimitiveTypeMapper.toProductPrimitiveType(productPrimitiveTypeDto);
        productPrimitiveTypeRepository.save(primitiveType);
        return productPrimitiveTypeMapper.fromProductPrimitiveType(primitiveType);
    }

    @Transactional
    @Override
    public void update(Long id, ProductPrimitiveTypeDto productPrimitiveTypeDto) {
        productPrimitiveTypeRepository.findById(id)
                .map(productPrimitiveType -> {
                    productPrimitiveType.setShortName(productPrimitiveTypeDto.getShortName());
                    productPrimitiveType.setLongName(productPrimitiveTypeDto.getLongName());
                    productPrimitiveType.setFloat(productPrimitiveTypeDto.isFloat());

                    return productPrimitiveType;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                               "Tipo Primitivo não encontrado não encontrado."));

    }

    @Override
    public ProductPrimitiveTypeDto findById(Long id) {
        return productPrimitiveTypeRepository.findById(id)
                .map(productPrimitiveTypeMapper::fromProductPrimitiveType)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                               "Tipo Primitivo não encontrado não encontrado."));
    }

    @Override
    public PageDto<ProductPrimitiveTypeDto> findAll(Example<ProductPrimitiveType> filter, Integer page, Integer size,
            Sort.Order order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        Page<ProductPrimitiveType> result = productPrimitiveTypeRepository.findAll(filter, pageable);

        return PageDto.<ProductPrimitiveTypeDto>builder()
                .data(result.getContent().stream().map(productPrimitiveTypeMapper::fromProductPrimitiveType)
                              .collect(Collectors.toList()))
                .pageNumber(result.getNumber())
                .pageSize(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .last(result.isLast())
                .build();
    }

    @Override
    public void delete(Long id) {
        productPrimitiveTypeRepository.findById(id)
                .map(productPrimitiveType -> {
                    productPrimitiveTypeRepository.delete(productPrimitiveType);
                    return productPrimitiveType;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                               "Tipo Primitivo não encontrado não encontrado."));
    }
}
