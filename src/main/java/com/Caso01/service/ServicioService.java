package com.Caso01.service;

import com.Caso01.domain.Servicio;
import com.Caso01.repository.ServicioRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepository servicioRepository;

    @Transactional(readOnly = true)
    public List<Servicio> listar() {
        return servicioRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Servicio> buscar(Integer id) {
        return servicioRepository.findById(id);
    }
    
    @Transactional
    public void guardar(Servicio servicio) {
        servicioRepository.save(servicio);
    }
    
    @Transactional
    public void eliminar(Integer id) {
        if (!servicioRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "El servicio con ID " + id + " no existe.");
        }
        try {
            servicioRepository.deleteById(id);
            servicioRepository.flush();
        } catch (DataIntegrityViolationException e) {
            log.warn("No fue posible eliminar el servicio con ID {}", id, e);
            throw new IllegalStateException(
                    "No se puede eliminar el servicio porque tiene citas asociadas.",
                    e);
        }
    }
}
