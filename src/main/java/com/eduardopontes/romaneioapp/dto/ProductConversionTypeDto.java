package com.eduardopontes.romaneioapp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class ProductConversionTypeDto implements Serializable {

    private static final long serialVersionUID = -2287193976978809118L;

    private Long id;

    @NotNull
    private double fromPrimary;

    @NotNull
    private double toTarget;

    @NotNull
    private ProductPrimitiveTypeDto targetProductPrimitiveType;
}