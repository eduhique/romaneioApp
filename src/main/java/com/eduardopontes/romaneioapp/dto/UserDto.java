package com.eduardopontes.romaneioapp.dto;

import com.eduardopontes.romaneioapp.model.user.Function;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto implements Serializable {

    private static final long serialVersionUID = -7880764064600223710L;

    private Long id;

    @NotEmpty
    @Size(max = 150)
    private String name;

    @NotEmpty
    @Size(max = 50)
    private String nickname;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @Size(min = 6)
    private String password;

    @NotNull
    private boolean active;

    @NotNull
    private Function function;

    private LocalDateTime createdDate;

    private LocalDateTime lastUpdate;
}
