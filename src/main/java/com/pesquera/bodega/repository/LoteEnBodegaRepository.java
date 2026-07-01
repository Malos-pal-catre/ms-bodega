package com.pesquera.bodega.repository;

import com.pesquera.bodega.model.LoteEnBodega;
import com.pesquera.bodega.model.EstadoLote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoteEnBodegaRepository extends JpaRepository<LoteEnBodega, Long> {

    List<LoteEnBodega> findByCompradorId(Long compradorId);
    List<LoteEnBodega> findByEstado(EstadoLote estado);
    List<LoteEnBodega> findByBodegaId(Long bodegaId);
    List<LoteEnBodega> findBySubastaId(Long subastaId);

    @Query("SELECT l FROM LoteEnBodega l WHERE l.bodega.id = :bodegaId AND l.estado = 'EN_BODEGA'")
    List<LoteEnBodega> findLotesEnBodegaByBodega(@Param("bodegaId") Long bodegaId);

    @Query("SELECT l FROM LoteEnBodega l WHERE l.compradorId = :compradorId AND l.estado = 'EN_BODEGA'")
    List<LoteEnBodega> findLotesPendientesRetiro(@Param("compradorId") Long compradorId);

    @Query(value = "SELECT * FROM lotes_bodega WHERE estado = 'EN_BODEGA' ORDER BY fecha_ingreso ASC", nativeQuery = true)
    List<LoteEnBodega> findTodosLotesEnBodega();
}