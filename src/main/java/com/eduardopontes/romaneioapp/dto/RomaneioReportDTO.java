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


    public Map<String, RomaneioReportSubtotalDto> getTotals() {
        var result = new HashMap<String, RomaneioReportSubtotalDto>();
        productReports.forEach(productReportDto -> {
            String key = productReportDto.getProduct().getProductType().getProductPrimitiveType().getLongName();
            if (result.containsKey(
                    key)) {
                var reportSubtotalDto = result.get(key);
                reportSubtotalDto.addQuantity(productReportDto.getQuantity());
                result.put(key,
                           reportSubtotalDto);
            } else {
                var romaneioReportSubtotalDto = new RomaneioReportSubtotalDto();
                romaneioReportSubtotalDto.setQuantity(productReportDto.getQuantity());
                romaneioReportSubtotalDto.setProductPrimitiveType(
                        productReportDto.getProduct().getProductType().getProductPrimitiveType());
                result.put(key, romaneioReportSubtotalDto);
            }
            subSum(result, productReportDto);
        });
        return result;
    }

    private void subSum(HashMap<String, RomaneioReportSubtotalDto> result, ProductReportDto productReportDto) {
        productReportDto.getProduct().getProductType().getProductConversionTypes()
                .forEach(productConversionTypeDto -> {
                    var subTotal =
                            (productReportDto.getQuantity() * productConversionTypeDto.getToTarget()) /
                            productConversionTypeDto.getFromPrimary();
                    String key1 = productConversionTypeDto.getTargetProductPrimitiveType().getLongName();
                    if (result.containsKey(key1)) {
                        var reportSubtotalDto = result.get(key1);
                        reportSubtotalDto.addQuantity(subTotal);
                        result.put(key1,
                                   reportSubtotalDto);
                    } else {
                        var romaneioReportSubtotalDto = new RomaneioReportSubtotalDto();
                        romaneioReportSubtotalDto.setQuantity(subTotal);
                        romaneioReportSubtotalDto.setProductPrimitiveType(
                                productConversionTypeDto.getTargetProductPrimitiveType());
                        result.put(key1, romaneioReportSubtotalDto);
                    }
                });
    }


}
