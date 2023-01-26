package com.eduardopontes.romaneioapp.dto.mapper;

import com.eduardopontes.romaneioapp.dto.UserResumeDto;
import com.eduardopontes.romaneioapp.model.user.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserResumeMapper {

    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    User toUser(UserResumeDto userResumeDto);

    @InheritInverseConfiguration
    @Mapping(target = "function", expression = "java(user.getFunction().name())")
    UserResumeDto fromUser(User user);
}
