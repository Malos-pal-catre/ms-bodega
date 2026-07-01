package com.pesquera.bodega.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bodegas")
public class Bodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer capacidadMaxima;

    @Column(nullable = false)
    private Integer ocupacionActual;

    @Column(nullable = false)
    private Double temperaturaActual;

    @Column(nullable = false)
    private Boolean activa = true;
}