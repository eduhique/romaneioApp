package com.eduardopontes.romaneioapp.dto;

import com.eduardopontes.romaneioapp.model.product.ProductPrimitiveType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class ProductTypeDto implements Serializable {


    private static final long serialVersionUID = 8829421967969327078L;

    private Long id;

    @NotNull
    private ProductPrimitiveType productPrimitiveType;

    private Set<ProductConversionTypeDto> productConversionTypes;

}
