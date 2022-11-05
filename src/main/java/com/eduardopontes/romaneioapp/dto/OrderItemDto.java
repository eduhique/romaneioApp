package com.eduardopontes.romaneioapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderItemDto implements Serializable {

    private static final long serialVersionUID = -8709002799213468555L;

    private Long id;

    private ProductDto product;

    private double amount;

    private ProductPrimitiveTypeDto productPrimitiveType;

    private LocalDateTime createdDate;

    private LocalDateTime lastUpdate;

    private boolean detached;

    private boolean conferred;
}

