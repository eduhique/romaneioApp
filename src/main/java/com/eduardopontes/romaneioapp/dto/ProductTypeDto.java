package com.eduardopontes.romaneioapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class ProductTypeDto extends ProductPrimitiveTypeDto implements Serializable {

    private static final long serialVersionUID = -1428050245095609828L;

    private Set<ProductConversionTypeDto> productConversionTypes;

}
