package com.pesquera.bodega.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LoteEnBodegaResponseDTO {
    private Long id;
    private Long bodegaId;
    private String nombreBodega;
    private Long subastaId;
    private Long compradorId;
    private String especie;
    private Double kilos;
    private String estado;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaRetiro;
    private String horarioRetiro;
}