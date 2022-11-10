package com.eduardopontes.romaneioapp.service;

import com.eduardopontes.romaneioapp.model.user.User;
import com.eduardopontes.romaneioapp.model.user.UserRole;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface UserRoleService {
    Set<UserRole> save(User user);

    void update(User user);
}
