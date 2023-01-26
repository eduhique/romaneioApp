package com.eduardopontes.romaneioapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RomaneioReportSubtotalDto {

    private double quantity;

    private ProductPrimitiveTypeDto productPrimitiveType;

    public void addQuantity(Double add) {
        this.quantity += add;

    }

}
