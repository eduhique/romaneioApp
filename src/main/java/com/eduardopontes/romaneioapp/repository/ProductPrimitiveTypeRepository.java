package com.eduardopontes.romaneioapp.repository;

import com.eduardopontes.romaneioapp.model.product.ProductPrimitiveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductPrimitiveTypeRepository extends JpaRepository<ProductPrimitiveType, Long> {

    Optional<ProductPrimitiveType> findByShortName(String shortName);
}