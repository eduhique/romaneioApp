package com.eduardopontes.romaneioapp.model.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotEmpty
    @Column(nullable = false, length = 150)
    @Size(max = 150)
    private String longName;

    @NotEmpty
    @Column(nullable = false, length = 5)
    @Size(max = 5)
    private String shortName;

    @NotNull
    @Column(nullable = false)
    private boolean Float;

    @OneToMany(mappedBy = "productType", fetch = FetchType.EAGER)
    private Set<ProductConversionType> productConversionTypes;

}
