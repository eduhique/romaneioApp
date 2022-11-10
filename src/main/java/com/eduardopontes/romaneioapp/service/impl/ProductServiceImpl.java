package com.eduardopontes.romaneioapp.service.impl;

import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.ProductDto;
import com.eduardopontes.romaneioapp.dto.mapper.ProductMapper;
import com.eduardopontes.romaneioapp.model.product.Product;
import com.eduardopontes.romaneioapp.repository.ProductRepository;
import com.eduardopontes.romaneioapp.service.ProductService;
import com.eduardopontes.romaneioapp.service.ProductTypeService;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductTypeService productTypeService;

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductTypeService productTypeService,
            ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productTypeService = productTypeService;
        this.productMapper = productMapper;
    }

    @Transactional
    @Override
    public ProductDto save(ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);
        productTypeService.save(product.getProductType());
        productRepository.save(product);
        return productMapper.fromProduct(product);
    }

    @Transactional
    @Override
    public void update(Long id, ProductDto productDto) {
        productRepository.findById(id)
                .map(product -> {
                    product.setName(productDto.getName());
                    product.setActive(productDto.isActive());
                    productTypeService.updateForObj(product.getProductType(), productDto.getProductType());
                    productRepository.save(product);
                    return product;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @Override
    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::fromProduct)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @Override
    public PageDto<ProductDto> findAll(Example<Product> filter, Integer page, Integer size, Sort.Order order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        Page<Product> result = productRepository.findAll(filter, pageable);

        return PageDto.<ProductDto>builder()
                .data(result.getContent().stream().map(productMapper::fromProduct).collect(Collectors.toList()))
                .pageNumber(result.getNumber())
                .pageSize(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .last(result.isLast())
                .build();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return product;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }
}
