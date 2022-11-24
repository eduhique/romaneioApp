package com.eduardopontes.romaneioapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class UserPasswordChangeRequest implements Serializable {

    private static final long serialVersionUID = -8080343533447908455L;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @Size(min = 6, max = 64, message = "A senha deve ter entre 6 e 64 caracteres.")
    private String newPassword;
}
