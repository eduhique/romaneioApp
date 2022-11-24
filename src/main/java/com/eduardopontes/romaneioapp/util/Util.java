package com.eduardopontes.romaneioapp.util;

import com.eduardopontes.romaneioapp.exception.BadRequestException;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

public class Util {

    private Util() {
        throw new IllegalStateException("Utility class");
    }

    public static Sort.Direction getDirection(String direction) {
        if (direction == null || Arrays
                .stream(Sort.Direction.values())
                .noneMatch(directionEnum -> directionEnum.name().equalsIgnoreCase(direction))) {
            throw new BadRequestException("O campo 'direction' só aceita as variáveis 'ASC' e 'DESC'.");
        }
        return Sort.Direction.valueOf(direction.toUpperCase());
    }
}
