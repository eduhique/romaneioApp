package com.eduardopontes.romaneioapp.config;

import com.eduardopontes.romaneioapp.dto.UserDto;
import com.eduardopontes.romaneioapp.exception.BadRequestException;
import com.eduardopontes.romaneioapp.model.user.Function;
import com.eduardopontes.romaneioapp.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Bean
    public CommandLineRunner loadData(UserService userService) {
        return args -> {
            UserDto user = new UserDto();

            user.setName(username);
            user.setNickname(username);
            user.setPassword(password);
            user.setActive(true);
            user.setFunction(Function.ADMINISTRADOR);

            try {
                userService.save(user);
            } catch (BadRequestException ignored) {
            }
        };
    }
}
