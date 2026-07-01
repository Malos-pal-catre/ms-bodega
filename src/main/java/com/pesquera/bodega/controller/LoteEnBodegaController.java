package com.pesquera.bodega.controller;

import com.pesquera.bodega.dto.LoteEnBodegaMapper;
import com.pesquera.bodega.dto.LoteEnBodegaRequestDTO;
import com.pesquera.bodega.dto.LoteEnBodegaResponseDTO;
import com.pesquera.bodega.model.Bodega;
import com.pesquera.bodega.model.LoteEnBodega;
import com.pesquera.bodega.model.EstadoLote;
import com.pesquera.bodega.service.BodegaService;
import com.pesquera.bodega.service.LoteEnBodegaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lotes-bodega")
@RequiredArgsConstructor
@Tag(name = "Lotes en Bodega", description = "Gestión de lotes almacenados en bodega frigorífica - Caleta Lo Abarca")
public class LoteEnBodegaController {

    private final LoteEnBodegaService loteService;
    private final BodegaService bodegaService;

    @PostMapping
    @Operation(
            summary = "Ingresar un lote a bodega",
            description = "Registra el ingreso de un lote proveniente de una subasta a una bodega frigorífica específica."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lote ingresado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "LoteIngresado",
                                    value = "{\"id\":1,\"bodegaId\":1,\"nombreBodega\":\"Bodega Frigorífica A\",\"subastaId\":10,\"compradorId\":3,\"especie\":\"Loco\",\"kilos\":120.5,\"estado\":\"EN_BODEGA\",\"fechaIngreso\":\"2026-06-30T10:15:00\",\"fechaRetiro\":null,\"horarioRetiro\":null}"
                            ))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "ErrorValidacion",
                                    value = "{\"kilos\":\"Los kilos deben ser mayor a 0\"}"
                            ))),
            @ApiResponse(responseCode = "404", description = "Bodega no encontrada")
    })
    public ResponseEntity<LoteEnBodegaResponseDTO> ingresarLote(
            @org.springframework.web.bind.annotation.RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del lote a ingresar a bodega",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LoteEnBodegaRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "NuevoLote",
                                    value = "{\"bodegaId\":1,\"subastaId\":10,\"compradorId\":3,\"especie\":\"Loco\",\"kilos\":120.5,\"horarioRetiro\":\"2026-07-01 09:00\"}"
                            )))
            @Valid LoteEnBodegaRequestDTO dto) {
        Bodega bodega = bodegaService.obtenerPorId(dto.getBodegaId());
        LoteEnBodega lote = LoteEnBodegaMapper.toEntity(dto);
        lote.setBodega(bodega);
        return ResponseEntity.status(HttpStatus.CREATED).body(LoteEnBodegaMapper.toDTO(loteService.ingresarLote(lote)));
    }

    @GetMapping
    @Operation(
            summary = "Listar todos los lotes en bodega",
            description = "Retorna el listado completo de lotes registrados, en cualquier estado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "ListadoLotes",
                                    value = "[{\"id\":1,\"bodegaId\":1,\"nombreBodega\":\"Bodega Frigorífica A\",\"subastaId\":10,\"compradorId\":3,\"especie\":\"Loco\",\"kilos\":120.5,\"estado\":\"EN_BODEGA\",\"fechaIngreso\":\"2026-06-30T10:15:00\",\"fechaRetiro\":null,\"horarioRetiro\":null}]"
                            )))
    })
    public ResponseEntity<List<LoteEnBodegaResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(loteService.obtenerTodos().stream().map(LoteEnBodegaMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar lote por ID",
            description = "Retorna los datos de un lote en bodega específico según su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lote encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "LoteEncontrado",
                                    value = "{\"id\":1,\"bodegaId\":1,\"nombreBodega\":\"Bodega Frigorífica A\",\"subastaId\":10,\"compradorId\":3,\"especie\":\"Loco\",\"kilos\":120.5,\"estado\":\"EN_BODEGA\",\"fechaIngreso\":\"2026-06-30T10:15:00\",\"fechaRetiro\":null,\"horarioRetiro\":null}"
                            ))),
            @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    public ResponseEntity<LoteEnBodegaResponseDTO> obtenerPorId(
            @Parameter(description = "ID del lote", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(LoteEnBodegaMapper.toDTO(loteService.obtenerPorId(id)));
    }

    @GetMapping("/comprador/{compradorId}")
    @Operation(
            summary = "Listar lotes por comprador",
            description = "Retorna todos los lotes en bodega asociados a un comprador específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de lotes del comprador",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "LotesPorComprador",
                                    value = "[{\"id\":1,\"bodegaId\":1,\"nombreBodega\":\"Bodega Frigorífica A\",\"subastaId\":10,\"compradorId\":3,\"especie\":\"Loco\",\"kilos\":120.5,\"estado\":\"EN_BODEGA\",\"fechaIngreso\":\"2026-06-30T10:15:00\",\"fechaRetiro\":null,\"horarioRetiro\":null}]"
                            )))
    })
    public ResponseEntity<List<LoteEnBodegaResponseDTO>> obtenerPorComprador(
            @Parameter(description = "ID del comprador", example = "3")
            @PathVariable Long compradorId) {
        return ResponseEntity.ok(loteService.obtenerPorComprador(compradorId).stream().map(LoteEnBodegaMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/estado/{estado}")
    @Operation(
            summary = "Listar lotes por estado",
            description = "Retorna los lotes filtrados por estado: EN_BODEGA, RETIRADO o VENCIDO."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de lotes por estado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "LotesPorEstado",
                                    value = "[{\"id\":1,\"bodegaId\":1,\"nombreBodega\":\"Bodega Frigorífica A\",\"subastaId\":10,\"compradorId\":3,\"especie\":\"Loco\",\"kilos\":120.5,\"estado\":\"EN_BODEGA\",\"fechaIngreso\":\"2026-06-30T10:15:00\",\"fechaRetiro\":null,\"horarioRetiro\":null}]"
                            )))
    })
    public ResponseEntity<List<LoteEnBodegaResponseDTO>> obtenerPorEstado(
            @Parameter(description = "Estado del lote", example = "EN_BODEGA")
            @PathVariable EstadoLote estado) {
        return ResponseEntity.ok(loteService.obtenerPorEstado(estado).stream().map(LoteEnBodegaMapper::toDTO).collect(Collectors.toList()));
    }

    @PutMapping("/{id}/retirar")
    @Operation(
            summary = "Retirar un lote de bodega",
            description = "Registra el retiro de un lote por parte del comprador, actualizando su estado a RETIRADO."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lote retirado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "LoteRetirado",
                                    value = "{\"id\":1,\"bodegaId\":1,\"nombreBodega\":\"Bodega Frigorífica A\",\"subastaId\":10,\"compradorId\":3,\"especie\":\"Loco\",\"kilos\":120.5,\"estado\":\"RETIRADO\",\"fechaIngreso\":\"2026-06-30T10:15:00\",\"fechaRetiro\":\"2026-07-01T09:05:00\",\"horarioRetiro\":\"2026-07-01 09:00\"}"
                            ))),
            @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    public ResponseEntity<LoteEnBodegaResponseDTO> retirarLote(
            @Parameter(description = "ID del lote a retirar", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Horario en que se realiza el retiro", example = "2026-07-01 09:00")
            @RequestParam String horarioRetiro) {
        return ResponseEntity.ok(LoteEnBodegaMapper.toDTO(loteService.retirarLote(id, horarioRetiro)));
    }
}