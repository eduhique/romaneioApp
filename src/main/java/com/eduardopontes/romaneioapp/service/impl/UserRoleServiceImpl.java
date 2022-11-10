package com.eduardopontes.romaneioapp.service.impl;

import com.eduardopontes.romaneioapp.model.user.Function;
import com.eduardopontes.romaneioapp.model.user.Role;
import com.eduardopontes.romaneioapp.model.user.User;
import com.eduardopontes.romaneioapp.model.user.UserRole;
import com.eduardopontes.romaneioapp.repository.UserRoleRepository;
import com.eduardopontes.romaneioapp.service.UserRoleService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Set<UserRole> save(User user) {
        Set<Role> roleSet = getRoles(user.getFunction());
        return Set.copyOf(userRoleRepository.saveAll(roleSet
                                                             .stream()
                                                             .map(role -> {
                                                                 UserRole userRole = new UserRole();
                                                                 userRole.setUser(user);
                                                                 userRole.setRole(role);
                                                                 return userRole;
                                                             })
                                                             .collect(Collectors.toList())));


    }

    @Override
    public void update(User user) {
        userRoleRepository.deleteAll(user.getRoles());
        save(user);
    }

    private Set<Role> getRoles(Function function) {
        switch (function) {
            case ADMINISTRADOR:
            case GERENTE:
            case CAIXA:
            case CONFERENTE:
                break;
        }

        return new HashSet<>();
    }
}
