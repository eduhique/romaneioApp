package com.eduardopontes.romaneioapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtDto {

    private String token;

    private String prefix;

    private Long expiry;
}
