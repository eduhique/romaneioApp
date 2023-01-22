package com.eduardopontes.romaneioapp.repository;

import com.eduardopontes.romaneioapp.model.romaneio.Romaneio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RomaneioRepository extends JpaRepository<Romaneio, Long> {

    List<Romaneio> findByActiveIsTrue();
}