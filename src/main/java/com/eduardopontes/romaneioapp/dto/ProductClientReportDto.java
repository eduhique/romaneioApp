package com.eduardopontes.romaneioapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ProductClientReportDto implements Serializable {

    private static final long serialVersionUID = 6731005931407515169L;

    private ClientDto client;

    private OrderItemDto orderItem;
}
