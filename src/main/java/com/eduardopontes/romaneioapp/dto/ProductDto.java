package com.eduardopontes.romaneioapp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class
ProductDto implements Serializable {

    private static final long serialVersionUID = -411171274903382143L;

    private Long id;

    @NotEmpty
    @Size(max = 150)
    private String name;

    @NotNull
    private boolean active;

    @NotNull
    private ProductTypeDto productType;

    private LocalDateTime createdDate;

    private LocalDateTime lastUpdate;
}
