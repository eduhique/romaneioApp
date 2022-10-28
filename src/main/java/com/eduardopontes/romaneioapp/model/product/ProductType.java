package com.eduardopontes.romaneioapp.model.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
public class ProductType extends ProductPrimitiveType implements Serializable {


    private static final long serialVersionUID = -4641225990977664610L;

    @OneToMany(mappedBy = "productType", fetch = FetchType.EAGER)
    private Set<ProductConversionType> productConversionTypes;

}
