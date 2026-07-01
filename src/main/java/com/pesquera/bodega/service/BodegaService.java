package com.pesquera.bodega.service;

import com.pesquera.bodega.exception.RecursoNoEncontradoException;
import com.pesquera.bodega.model.Bodega;
import com.pesquera.bodega.repository.BodegaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BodegaService {

    private final BodegaRepository bodegaRepository;

    public Bodega crearBodega(Bodega bodega) {
        bodega.setOcupacionActual(0);
        return bodegaRepository.save(bodega);
    }

    public List<Bodega> obtenerTodas() {
        return bodegaRepository.findAll();
    }

    public Bodega obtenerPorId(Long id) {
        return bodegaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Bodega no encontrada con id: " + id));
    }

    public List<Bodega> obtenerActivas() {
        return bodegaRepository.findByActiva(true);
    }

    public Bodega actualizarTemperatura(Long id, Double temperatura) {
        Bodega bodega = obtenerPorId(id);
        bodega.setTemperaturaActual(temperatura);
        return bodegaRepository.save(bodega);
    }

    public Bodega desactivarBodega(Long id) {
        Bodega bodega = obtenerPorId(id);
        bodega.setActiva(false);
        return bodegaRepository.save(bodega);
    }
}