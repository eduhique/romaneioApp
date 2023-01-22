package com.eduardopontes.romaneioapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ProductReportDto implements Serializable {

    private static final long serialVersionUID = 3488249344329868806L;

    private ProductDto product;

    private Double quantity;

    private List<ProductClientReportDto> productClientReports;
}
