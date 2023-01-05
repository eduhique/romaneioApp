package com.eduardopontes.romaneioapp.repository;

import com.eduardopontes.romaneioapp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(String nickName);

    Optional<User> findByNickname(String nickName);
}