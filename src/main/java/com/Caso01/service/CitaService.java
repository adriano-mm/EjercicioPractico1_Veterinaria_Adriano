package com.Caso01.service;

import com.Caso01.domain.Cita;
import com.Caso01.repository.CitaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CitaService {
    private final CitaRepository citaRepository;
    
    @Transactional(readOnly = true)
    public List<Cita> listar() {
        return citaRepository.findAll();
    }
}