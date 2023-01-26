package com.eduardopontes.romaneioapp.dto;

import com.eduardopontes.romaneioapp.model.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDto implements Serializable {

    private static final long serialVersionUID = 8472361834062221810L;

    private Long id;

    private RomaneioDto romaneio;

    private ClientDto client;

    private UserDto user;

    private List<OrderItemDto> orderItems;

    private LocalDateTime createdDate;

    private LocalDateTime lastUpdate;

    private OrderStatus status;

    private LocalDateTime statusDate;
}

