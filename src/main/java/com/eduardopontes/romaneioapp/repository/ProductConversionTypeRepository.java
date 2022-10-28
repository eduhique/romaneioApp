package com.eduardopontes.romaneioapp.repository;

import com.eduardopontes.romaneioapp.model.product.ProductConversionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductConversionTypeRepository extends JpaRepository<ProductConversionType, Long> {
}