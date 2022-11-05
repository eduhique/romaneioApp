package com.eduardopontes.romaneioapp.dto.mapper;

import com.eduardopontes.romaneioapp.dto.UserDto;
import com.eduardopontes.romaneioapp.model.user.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(UserDto userDto);

    @InheritInverseConfiguration
    UserDto fromUser(User user);
}
