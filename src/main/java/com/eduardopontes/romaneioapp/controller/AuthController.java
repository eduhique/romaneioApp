package com.eduardopontes.romaneioapp.controller;

import com.eduardopontes.romaneioapp.dto.CredentialsDto;
import com.eduardopontes.romaneioapp.dto.JwtDto;
import com.eduardopontes.romaneioapp.exception.InvalidPasswordException;
import com.eduardopontes.romaneioapp.model.user.User;
import com.eduardopontes.romaneioapp.security.jwt.JWTService;
import com.eduardopontes.romaneioapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final UserService userService;

    private final JWTService jwtService;


    public AuthController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public JwtDto auth(@RequestBody CredentialsDto credentialsDto) {
        try {
            User user = userService.auth(credentialsDto);
            return jwtService.tokenGenerate(user);

        } catch (InvalidPasswordException | ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou Senha Inválidos.");
        }
    }
}
