package com.pesquera.bodega.repository;

import com.pesquera.bodega.model.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BodegaRepository extends JpaRepository<Bodega, Long> {

    List<Bodega> findByActiva(Boolean activa);

    @Query("SELECT b FROM Bodega b WHERE b.activa = true AND b.ocupacionActual < b.capacidadMaxima")
    List<Bodega> findBodegasDisponibles();

    @Query("SELECT b FROM Bodega b WHERE b.temperaturaActual < :temp")
    List<Bodega> findByTemperaturaActualLessThan(@Param("temp") Double temp);

    @Query(value = "SELECT * FROM bodegas WHERE activa = true ORDER BY ocupacion_actual ASC", nativeQuery = true)
    List<Bodega> findBodegasActivasOrdenadas();
}