package com.eduardopontes.romaneioapp.repository;

import com.eduardopontes.romaneioapp.model.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}