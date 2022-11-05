package com.eduardopontes.romaneioapp.dto;

import com.eduardopontes.romaneioapp.model.client.ClientType;
import com.eduardopontes.romaneioapp.model.order.Order;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class ClientDto implements Serializable {

    private static final long serialVersionUID = 8639711803131941064L;

    private Long id;

    private String name;

    private String district;

    private ClientType clientType;

    private Set<Order> orders;

    private LocalDateTime createdDate;

    private LocalDateTime lastUpdate;

    private String comments;
}