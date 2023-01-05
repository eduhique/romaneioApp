package com.eduardopontes.romaneioapp.service;

import com.eduardopontes.romaneioapp.model.user.User;
import org.springframework.stereotype.Service;

@Service
public interface UserRoleService {
    void save(User user);

    void update(User user);
}
