package com.eduardopontes.romaneioapp.model.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "product_conversion_type")
public class ProductConversionType implements Serializable {


    private static final long serialVersionUID = 3031752560337665799L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_conversion_type_sequence")
    @SequenceGenerator(name = "product_conversion_type_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    @NotNull
    @Column(precision = 10, scale = 3)
    @Min(1)
    private Double fromPrimary;

    @NotNull
    @Column(precision = 10, scale = 3)
    @Min(1)
    private Double toTarget;

    @OneToOne
    @NotNull
    @JoinColumn(name = "product_primitive_type_id", nullable = false)
    private ProductPrimitiveType targetProductPrimitiveType;
}
