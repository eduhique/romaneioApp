package com.eduardopontes.romaneioapp.repository;

import com.eduardopontes.romaneioapp.model.product.ProductPrimitiveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPrimitiveTypeRepository extends JpaRepository<ProductPrimitiveType, Long> {
}