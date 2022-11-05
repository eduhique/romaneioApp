package com.eduardopontes.romaneioapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ApiErrors {

    private List<String> errors;

    public ApiErrors(String mensagemErro) {
        this.errors = Collections.singletonList(mensagemErro);
    }
}
