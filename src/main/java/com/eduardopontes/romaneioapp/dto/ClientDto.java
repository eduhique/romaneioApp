package com.eduardopontes.romaneioapp.dto;

import com.eduardopontes.romaneioapp.model.client.ClientType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ClientDto implements Serializable {

    private static final long serialVersionUID = 8639711803131941064L;

    private Long id;

    private String name;

    private String district;

    private ClientType clientType;

    private LocalDateTime createdDate;

    private LocalDateTime lastUpdate;

    private String comments;
}