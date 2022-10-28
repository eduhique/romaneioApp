package com.eduardopontes.romaneioapp.repository;

import com.eduardopontes.romaneioapp.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}