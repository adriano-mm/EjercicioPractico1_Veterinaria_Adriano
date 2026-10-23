package com.Caso01.service;

import com.Caso01.domain.TipoServicio;
import com.Caso01.repository.TipoServicioRepository;
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
public class TipoServicioService {
    private final TipoServicioRepository repository;

    @Transactional(readOnly = true)
    public List<TipoServicio> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<TipoServicio> buscar(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public void guardar(TipoServicio tipoServicio) {
        repository.save(tipoServicio);
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException(
                    "El tipo de servicio con ID " + id + " no existe.");
        }
        try {
            repository.deleteById(id);
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            log.warn("No fue posible eliminar el tipo de servicio con ID {}", id, e);
            throw new IllegalStateException(
                    "No se puede eliminar el tipo de servicio porque tiene servicios asociados.",
                    e);
        }
    }
}