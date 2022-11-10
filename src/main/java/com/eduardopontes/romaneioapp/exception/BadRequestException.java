package com.eduardopontes.romaneioapp.exception;

public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 3301194559858906197L;

    public BadRequestException(String s) {
        super(s);
    }
}
