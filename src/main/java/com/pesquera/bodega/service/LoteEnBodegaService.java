package com.pesquera.bodega.service;

import com.pesquera.bodega.exception.RecursoNoEncontradoException;
import com.pesquera.bodega.model.LoteEnBodega;
import com.pesquera.bodega.model.EstadoLote;
import com.pesquera.bodega.model.Bodega;
import com.pesquera.bodega.repository.LoteEnBodegaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoteEnBodegaService {

    private final LoteEnBodegaRepository loteRepository;
    private final BodegaService bodegaService;

    public LoteEnBodega ingresarLote(LoteEnBodega lote) {
        Bodega bodega = bodegaService.obtenerPorId(lote.getBodega().getId());
        if (bodega.getOcupacionActual() >= bodega.getCapacidadMaxima()) {
            throw new IllegalArgumentException("Bodega sin capacidad disponible");
        }
        lote.setEstado(EstadoLote.EN_BODEGA);
        lote.setFechaIngreso(LocalDateTime.now());
        bodega.setOcupacionActual(bodega.getOcupacionActual() + 1);
        bodegaService.crearBodega(bodega);
        return loteRepository.save(lote);
    }

    public List<LoteEnBodega> obtenerTodos() {
        return loteRepository.findAll();
    }

    public LoteEnBodega obtenerPorId(Long id) {
        return loteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Lote no encontrado con id: " + id));
    }

    public List<LoteEnBodega> obtenerPorComprador(Long compradorId) {
        return loteRepository.findByCompradorId(compradorId);
    }

    public List<LoteEnBodega> obtenerPorEstado(EstadoLote estado) {
        return loteRepository.findByEstado(estado);
    }

    public LoteEnBodega retirarLote(Long id, String horarioRetiro) {
        LoteEnBodega lote = obtenerPorId(id);
        lote.setEstado(EstadoLote.RETIRADO);
        lote.setFechaRetiro(LocalDateTime.now());
        lote.setHorarioRetiro(horarioRetiro);
        Bodega bodega = bodegaService.obtenerPorId(lote.getBodega().getId());
        bodega.setOcupacionActual(bodega.getOcupacionActual() - 1);
        bodegaService.crearBodega(bodega);
        return loteRepository.save(lote);
    }
}