package com.eduardopontes.romaneioapp.controller;

import com.eduardopontes.romaneioapp.config.ConstantValues;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.ProductDto;
import com.eduardopontes.romaneioapp.dto.mapper.ProductMapper;
import com.eduardopontes.romaneioapp.model.product.Product;
import com.eduardopontes.romaneioapp.service.ProductService;
import com.eduardopontes.romaneioapp.util.Util;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;

    private final ProductMapper productMapper;

    public ProductController(ProductService productService,
            ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto save(@Valid @RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        productService.update(id, productDto);
    }

    @GetMapping("{id}")
    public ProductDto findById(@PathVariable("id") Long id) {
        return productMapper.fromProduct(productService.findById(id));
    }

    @GetMapping
    public PageDto<ProductDto> findAll(Product product,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_PAGE_NUMBER, required = false) Integer page,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_PAGE_SIZE, required = false) Integer size,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_ORDER_BY, required = false) String order,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_ORDER_DIRECTION, required = false) String direction) {

        Sort.Direction directionEnum = Util.getDirection(direction);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<Product> example = Example.of(product, matcher);

        return productService.findAll(example, page, size, new Sort.Order(directionEnum, order));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
