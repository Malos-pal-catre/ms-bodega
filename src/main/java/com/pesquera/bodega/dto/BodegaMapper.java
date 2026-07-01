package com.pesquera.bodega.dto;

import com.pesquera.bodega.model.Bodega;

public class BodegaMapper {

    public static BodegaResponseDTO toDTO(Bodega bodega) {
        BodegaResponseDTO dto = new BodegaResponseDTO();
        dto.setId(bodega.getId());
        dto.setNombre(bodega.getNombre());
        dto.setCapacidadMaxima(bodega.getCapacidadMaxima());
        dto.setOcupacionActual(bodega.getOcupacionActual());
        dto.setTemperaturaActual(bodega.getTemperaturaActual());
        dto.setActiva(bodega.getActiva());
        return dto;
    }

    public static Bodega toEntity(BodegaRequestDTO dto) {
        Bodega bodega = new Bodega();
        bodega.setNombre(dto.getNombre());
        bodega.setCapacidadMaxima(dto.getCapacidadMaxima());
        bodega.setTemperaturaActual(dto.getTemperaturaActual());
        return bodega;
    }
}