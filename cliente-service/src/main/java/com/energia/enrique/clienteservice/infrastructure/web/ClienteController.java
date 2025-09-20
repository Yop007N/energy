package com.energia.enrique.clienteservice.infrastructure.web;

import com.energia.enrique.clienteservice.application.dto.CrearClienteRequest;
import com.energia.enrique.clienteservice.application.dto.ClienteResponse;
import com.energia.enrique.clienteservice.application.usecases.CrearClienteUseCase;
import com.energia.enrique.clienteservice.infrastructure.web.dto.ApiResponse;
import com.energia.shared.domain.exceptions.DomainValidationException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Controlador REST para gestión de clientes.
 * Capa de infraestructura - Clean Architecture.
 * Se encarga únicamente de la entrada HTTP y delegación a casos de uso.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@Validated
@Tag(name = "Cliente Controller", description = "API REST para gestión de clientes del sistema energético")
public class ClienteController {

    private final CrearClienteUseCase crearClienteUseCase;

    @PostMapping
    @Operation(
        summary = "Crear nuevo cliente",
        description = "Crea un nuevo cliente en el sistema con validaciones de negocio"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Cliente ya existe"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ApiResponse<ClienteResponse>> crearCliente(
            @Parameter(description = "Datos del cliente a crear", required = true)
            @Valid @RequestBody CrearClienteRequest request) {

        try {
            log.info("Solicitud de creación de cliente recibida: {}", request.getIdentificacion());

            ClienteResponse cliente = crearClienteUseCase.ejecutar(request);

            ApiResponse<ClienteResponse> response = ApiResponse.<ClienteResponse>builder()
                .success(true)
                .message("Cliente creado exitosamente")
                .data(cliente)
                .timestamp(LocalDateTime.now())
                .path("/api/v1/clientes")
                .build();

            log.info("Cliente creado exitosamente con ID: {}", cliente.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (DomainValidationException e) {
            log.warn("Error de validación al crear cliente: {}", e.getMessage());

            ApiResponse<ClienteResponse> response = ApiResponse.<ClienteResponse>builder()
                .success(false)
                .message("Error de validación")
                .error(ApiResponse.ErrorDetail.builder()
                    .code("VALIDATION_ERROR")
                    .message(e.getMessage())
                    .field(e.getCampo())
                    .timestamp(LocalDateTime.now())
                    .build())
                .timestamp(LocalDateTime.now())
                .path("/api/v1/clientes")
                .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {
            log.error("Error inesperado al crear cliente", e);

            ApiResponse<ClienteResponse> response = ApiResponse.<ClienteResponse>builder()
                .success(false)
                .message("Error interno del servidor")
                .error(ApiResponse.ErrorDetail.builder()
                    .code("INTERNAL_ERROR")
                    .message("Ha ocurrido un error interno. Contacte al administrador.")
                    .timestamp(LocalDateTime.now())
                    .build())
                .timestamp(LocalDateTime.now())
                .path("/api/v1/clientes")
                .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/health")
    @Operation(
        summary = "Verificar estado del servicio",
        description = "Endpoint para verificar que el servicio de clientes está funcionando"
    )
    public ResponseEntity<ApiResponse<Map<String, Object>>> health() {
        Map<String, Object> healthData = Map.of(
            "status", "UP",
            "service", "cliente-service",
            "version", "1.0.0",
            "timestamp", LocalDateTime.now()
        );

        ApiResponse<Map<String, Object>> response = ApiResponse.<Map<String, Object>>builder()
            .success(true)
            .message("Servicio funcionando correctamente")
            .data(healthData)
            .timestamp(LocalDateTime.now())
            .path("/api/v1/clientes/health")
            .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    @Operation(
        summary = "Información del servicio",
        description = "Obtiene información detallada del microservicio de clientes"
    )
    public ResponseEntity<ApiResponse<Map<String, Object>>> info() {
        Map<String, Object> infoData = Map.of(
            "serviceName", "Cliente Microservice",
            "version", "1.0.0",
            "description", "Microservicio para gestión de clientes del sistema energético",
            "architecture", "Clean Architecture",
            "technologies", Map.of(
                "framework", "Spring Boot 3.3.4",
                "language", "Java 17",
                "database", "PostgreSQL",
                "documentation", "OpenAPI 3"
            ),
            "endpoints", Map.of(
                "create", "POST /api/v1/clientes",
                "health", "GET /api/v1/clientes/health",
                "info", "GET /api/v1/clientes/info"
            ),
            "timestamp", LocalDateTime.now()
        );

        ApiResponse<Map<String, Object>> response = ApiResponse.<Map<String, Object>>builder()
            .success(true)
            .message("Información del servicio")
            .data(infoData)
            .timestamp(LocalDateTime.now())
            .path("/api/v1/clientes/info")
            .build();

        return ResponseEntity.ok(response);
    }
}