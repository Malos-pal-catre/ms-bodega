package com.pesquera.bodega.controller;

import com.pesquera.bodega.dto.BodegaMapper;
import com.pesquera.bodega.dto.BodegaRequestDTO;
import com.pesquera.bodega.dto.BodegaResponseDTO;
import com.pesquera.bodega.model.Bodega;
import com.pesquera.bodega.service.BodegaService;
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
@RequestMapping("/api/bodegas")
@RequiredArgsConstructor
@Tag(name = "Bodegas", description = "Gestión de bodega frigorífica - Caleta Lo Abarca")
public class BodegaController {

    private final BodegaService bodegaService;

    @PostMapping
    @Operation(
            summary = "Crear una nueva bodega",
            description = "Registra una nueva bodega frigorífica con su capacidad máxima y temperatura inicial."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bodega creada correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "BodegaCreada",
                                    value = "{\"id\":1,\"nombre\":\"Bodega Frigorífica A\",\"capacidadMaxima\":1000,\"ocupacionActual\":0,\"temperaturaActual\":-2.5,\"activa\":true}"
                            ))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "ErrorValidacion",
                                    value = "{\"nombre\":\"El nombre es obligatorio\"}"
                            )))
    })
    public ResponseEntity<BodegaResponseDTO> crearBodega(
            @org.springframework.web.bind.annotation.RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la bodega a crear",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BodegaRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "NuevaBodega",
                                    value = "{\"nombre\":\"Bodega Frigorífica A\",\"capacidadMaxima\":1000,\"temperaturaActual\":-2.5}"
                            )))
            @Valid BodegaRequestDTO dto) {
        Bodega bodega = BodegaMapper.toEntity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(BodegaMapper.toDTO(bodegaService.crearBodega(bodega)));
    }

    @GetMapping
    @Operation(
            summary = "Listar todas las bodegas",
            description = "Retorna el listado completo de bodegas registradas, activas e inactivas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "ListadoBodegas",
                                    value = "[{\"id\":1,\"nombre\":\"Bodega Frigorífica A\",\"capacidadMaxima\":1000,\"ocupacionActual\":250,\"temperaturaActual\":-2.5,\"activa\":true}]"
                            )))
    })
    public ResponseEntity<List<BodegaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(bodegaService.obtenerTodas().stream().map(BodegaMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar bodega por ID",
            description = "Retorna los datos de una bodega específica según su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bodega encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "BodegaEncontrada",
                                    value = "{\"id\":1,\"nombre\":\"Bodega Frigorífica A\",\"capacidadMaxima\":1000,\"ocupacionActual\":250,\"temperaturaActual\":-2.5,\"activa\":true}"
                            ))),
            @ApiResponse(responseCode = "404", description = "Bodega no encontrada")
    })
    public ResponseEntity<BodegaResponseDTO> obtenerPorId(
            @Parameter(description = "ID de la bodega", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(BodegaMapper.toDTO(bodegaService.obtenerPorId(id)));
    }

    @GetMapping("/activas")
    @Operation(
            summary = "Listar bodegas activas",
            description = "Retorna únicamente las bodegas que se encuentran operativas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de bodegas activas",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "BodegasActivas",
                                    value = "[{\"id\":1,\"nombre\":\"Bodega Frigorífica A\",\"capacidadMaxima\":1000,\"ocupacionActual\":250,\"temperaturaActual\":-2.5,\"activa\":true}]"
                            )))
    })
    public ResponseEntity<List<BodegaResponseDTO>> obtenerActivas() {
        return ResponseEntity.ok(bodegaService.obtenerActivas().stream().map(BodegaMapper::toDTO).collect(Collectors.toList()));
    }

    @PutMapping("/{id}/temperatura")
    @Operation(
            summary = "Actualizar temperatura de una bodega",
            description = "Actualiza el valor de temperatura actual registrado para la bodega indicada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temperatura actualizada correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "TemperaturaActualizada",
                                    value = "{\"id\":1,\"nombre\":\"Bodega Frigorífica A\",\"capacidadMaxima\":1000,\"ocupacionActual\":250,\"temperaturaActual\":-4.0,\"activa\":true}"
                            ))),
            @ApiResponse(responseCode = "404", description = "Bodega no encontrada")
    })
    public ResponseEntity<BodegaResponseDTO> actualizarTemperatura(
            @Parameter(description = "ID de la bodega", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nueva temperatura en grados Celsius", example = "-4.0")
            @RequestParam Double temperatura) {
        return ResponseEntity.ok(BodegaMapper.toDTO(bodegaService.actualizarTemperatura(id, temperatura)));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Desactivar una bodega",
            description = "Marca la bodega indicada como inactiva, sin eliminarla físicamente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bodega desactivada correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "BodegaDesactivada",
                                    value = "{\"id\":1,\"nombre\":\"Bodega Frigorífica A\",\"capacidadMaxima\":1000,\"ocupacionActual\":0,\"temperaturaActual\":-2.5,\"activa\":false}"
                            ))),
            @ApiResponse(responseCode = "404", description = "Bodega no encontrada")
    })
    public ResponseEntity<BodegaResponseDTO> desactivarBodega(
            @Parameter(description = "ID de la bodega a desactivar", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(BodegaMapper.toDTO(bodegaService.desactivarBodega(id)));
    }
}