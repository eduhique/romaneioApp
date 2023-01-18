package com.eduardopontes.romaneioapp.controller;

import com.eduardopontes.romaneioapp.dto.CredentialsDto;
import com.eduardopontes.romaneioapp.dto.JwtDto;
import com.eduardopontes.romaneioapp.dto.UserDto;
import com.eduardopontes.romaneioapp.dto.mapper.UserMapper;
import com.eduardopontes.romaneioapp.exception.InvalidAuthException;
import com.eduardopontes.romaneioapp.exception.UserInactiveException;
import com.eduardopontes.romaneioapp.model.user.User;
import com.eduardopontes.romaneioapp.security.jwt.JWTService;
import com.eduardopontes.romaneioapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final UserService userService;

    private final JWTService jwtService;

    private final UserMapper userMapper;


    public AuthController(UserService userService, JWTService jwtService, UserMapper userMapper) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public JwtDto auth(@RequestBody CredentialsDto credentialsDto) {
        try {
            User user = userService.auth(credentialsDto);
            return jwtService.tokenGenerate(user);

        } catch (InvalidAuthException | ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou Senha Inválidos.");
        } catch (UserInactiveException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                                              "Este usuário esta inativo, contate um administrador.");
        }
    }

    @GetMapping("current-user")
    public UserDto currentUser(@RequestHeader("Authorization") String token) {
        return userMapper.fromUser(
                userService.findByNickname(
                        jwtService.getSubject(token.split(" ")[1])
                )
        );

    }

    @GetMapping("validation")
    public boolean isValid(@RequestHeader("Authorization") String token) {
        return jwtService.isValid(token.split(" ")[1]);
    }
}
