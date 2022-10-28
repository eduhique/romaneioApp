package com.eduardopontes.romaneioapp.repository;

import com.eduardopontes.romaneioapp.model.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}