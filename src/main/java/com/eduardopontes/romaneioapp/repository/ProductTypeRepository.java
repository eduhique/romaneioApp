package com.eduardopontes.romaneioapp.repository;

import com.eduardopontes.romaneioapp.model.product.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
}