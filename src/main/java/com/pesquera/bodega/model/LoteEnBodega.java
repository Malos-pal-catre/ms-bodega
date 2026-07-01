package com.pesquera.bodega.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "lotes_bodega")
public class LoteEnBodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = false)
    private Bodega bodega;

    @Column(nullable = false)
    private Long subastaId;

    @Column(nullable = false)
    private Long compradorId;

    @Column(nullable = false)
    private String especie;

    @Column(nullable = false)
    private Double kilos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoLote estado;

    @Column(nullable = false)
    private LocalDateTime fechaIngreso;

    private LocalDateTime fechaRetiro;

    private String horarioRetiro;
}