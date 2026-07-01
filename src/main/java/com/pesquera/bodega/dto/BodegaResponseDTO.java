package com.pesquera.bodega.dto;

import lombok.Data;

@Data
public class BodegaResponseDTO {
    private Long id;
    private String nombre;
    private Integer capacidadMaxima;
    private Integer ocupacionActual;
    private Double temperaturaActual;
    private Boolean activa;
}