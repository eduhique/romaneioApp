package com.eduardopontes.romaneioapp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class ProductPrimitiveTypeDto implements Serializable {

    private static final long serialVersionUID = -7343534102664356783L;

    private Long id;

    @NotEmpty
    @Size(max = 150)
    private String longName;

    @NotEmpty
    @Size(max = 5)
    @Column(unique = true)
    private String shortName;

    @NotNull
    private boolean floatPoint;
}

