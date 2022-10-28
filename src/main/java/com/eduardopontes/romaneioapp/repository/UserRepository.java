package com.eduardopontes.romaneioapp.repository;

import com.eduardopontes.romaneioapp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}