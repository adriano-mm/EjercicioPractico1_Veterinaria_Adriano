package com.Caso01.repository;

import com.Caso01.domain.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository
        extends JpaRepository<Cita, Integer> {
}