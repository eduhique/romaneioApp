package com.eduardopontes.romaneioapp.model.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
public class ProductType implements Serializable {


    private static final long serialVersionUID = -4641225990977664610L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_type_sequence")
    @SequenceGenerator(name = "product_type_sequence", allocationSize = 1)
    private Long id;

    @OneToOne
    @NotNull
    @JoinColumn(name = "primitive_type_id", nullable = false)
    private ProductPrimitiveType productPrimitiveType;

    @OneToMany(mappedBy = "productType", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<ProductConversionType> productConversionTypes;

}
