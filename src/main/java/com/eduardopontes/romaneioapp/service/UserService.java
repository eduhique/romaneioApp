package com.eduardopontes.romaneioapp.service;

import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.UserDto;
import com.eduardopontes.romaneioapp.dto.UserPasswordChangeRequest;
import com.eduardopontes.romaneioapp.model.user.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

public interface UserService {


    UserDto save(UserDto userDto);

    void update(Long id, UserDto userDto);

    UserDto findById(Long id);

    PageDto<UserDto> findAll(Example<User> filter, Integer page, Integer size, Sort.Order order);

    void delete(Long id);

    void changePassword(Long id, UserPasswordChangeRequest changePassword);
}
