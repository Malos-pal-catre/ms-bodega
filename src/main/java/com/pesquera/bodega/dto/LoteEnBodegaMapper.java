package com.pesquera.bodega.dto;

import com.pesquera.bodega.model.LoteEnBodega;

public class LoteEnBodegaMapper {

    public static LoteEnBodegaResponseDTO toDTO(LoteEnBodega lote) {
        LoteEnBodegaResponseDTO dto = new LoteEnBodegaResponseDTO();
        dto.setId(lote.getId());
        dto.setBodegaId(lote.getBodega().getId());
        dto.setNombreBodega(lote.getBodega().getNombre());
        dto.setSubastaId(lote.getSubastaId());
        dto.setCompradorId(lote.getCompradorId());
        dto.setEspecie(lote.getEspecie());
        dto.setKilos(lote.getKilos());
        dto.setEstado(lote.getEstado().name());
        dto.setFechaIngreso(lote.getFechaIngreso());
        dto.setFechaRetiro(lote.getFechaRetiro());
        dto.setHorarioRetiro(lote.getHorarioRetiro());
        return dto;
    }

    public static LoteEnBodega toEntity(LoteEnBodegaRequestDTO dto) {
        LoteEnBodega lote = new LoteEnBodega();
        lote.setSubastaId(dto.getSubastaId());
        lote.setCompradorId(dto.getCompradorId());
        lote.setEspecie(dto.getEspecie());
        lote.setKilos(dto.getKilos());
        lote.setHorarioRetiro(dto.getHorarioRetiro());
        return lote;
    }
}