package com.eduardopontes.romaneioapp.controller;

import com.eduardopontes.romaneioapp.config.ConstantValues;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.ProductPrimitiveTypeDto;
import com.eduardopontes.romaneioapp.model.product.ProductPrimitiveType;
import com.eduardopontes.romaneioapp.service.ProductPrimitiveTypeService;
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
@RequestMapping(value = "/primitive-types")
public class ProductPrimitiveTypeController {

    private final ProductPrimitiveTypeService primitiveTypeService;

    public ProductPrimitiveTypeController(ProductPrimitiveTypeService primitiveTypeService) {
        this.primitiveTypeService = primitiveTypeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductPrimitiveTypeDto save(@Valid @RequestBody ProductPrimitiveTypeDto productPrimitiveTypeDto) {
        return primitiveTypeService.save(productPrimitiveTypeDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody ProductPrimitiveTypeDto productPrimitiveTypeDto) {
        primitiveTypeService.update(id, productPrimitiveTypeDto);
    }

    @GetMapping("{id}")
    public ProductPrimitiveTypeDto findById(@PathVariable("id") Long id) {
        return primitiveTypeService.findById(id);
    }

    @GetMapping
    public PageDto<ProductPrimitiveTypeDto> findAll(ProductPrimitiveType productPrimitiveType,
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
        Example<ProductPrimitiveType> example = Example.of(productPrimitiveType, matcher);

        return primitiveTypeService.findAll(example, page, size, new Sort.Order(directionEnum, order));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        primitiveTypeService.delete(id);
    }
}
