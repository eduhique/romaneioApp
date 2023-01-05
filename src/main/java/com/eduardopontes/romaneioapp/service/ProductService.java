package com.eduardopontes.romaneioapp.service;

import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.ProductDto;
import com.eduardopontes.romaneioapp.model.product.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

public interface ProductService {

    ProductDto save(ProductDto productDto);

    void update(Long id, ProductDto productDto);

    Product findById(Long id);

    PageDto<ProductDto> findAll(Example<Product> filter, Integer page, Integer size, Sort.Order order);

    void delete(Long id);

}
