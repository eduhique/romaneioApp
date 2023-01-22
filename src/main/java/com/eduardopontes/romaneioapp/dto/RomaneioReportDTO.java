package com.eduardopontes.romaneioapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class RomaneioReportDTO implements Serializable {

    private static final long serialVersionUID = 5432546974190034905L;

    private RomaneioDto romaneio;

    private List<ProductReportDto> productReports;


    public Map<String, Double> getTotals() {
        var result = new HashMap<String, Double>();
        productReports.forEach(productReportDto -> {
            if (result.containsKey(
                    productReportDto.getProduct().getProductType().getProductPrimitiveType().getLongName())) {
                result.put(productReportDto.getProduct().getProductType().getProductPrimitiveType().getLongName(),
                           Double.sum(result.get(
                                   productReportDto.getProduct().getProductType().getProductPrimitiveType()
                                           .getLongName()), productReportDto.getQuantity()));
            } else {
                result.put(productReportDto.getProduct().getProductType().getProductPrimitiveType().getLongName(),
                           productReportDto.getQuantity());
            }
        });
        return result;
    }


}
