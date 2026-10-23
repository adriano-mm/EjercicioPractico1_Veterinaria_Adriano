package com.Caso01.repository;

import com.Caso01.domain.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository
        extends JpaRepository<Servicio, Integer> {
}
