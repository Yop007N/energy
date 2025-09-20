package com.energia.enrique.clienteservice.controller;

import com.energia.enrique.clienteservice.model.ClienteEntity;
import com.energia.enrique.clienteservice.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@Tag(name = "Cliente Controller", description = "API para gestión de clientes del sistema energético")
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Obtener todos los clientes", description = "Retorna una lista paginada de todos los clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<ClienteEntity>> obtenerTodosClientes(Pageable pageable) {
        log.info("Solicitando lista de clientes - Página: {}, Tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<ClienteEntity> clientes = clienteService.obtenerTodosClientes(pageable);
        return ResponseEntity.ok(clientes);
    }

    @Operation(summary = "Obtener cliente por ID", description = "Retorna un cliente específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> obtenerClientePorId(
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long id) {

        if (id <= 0) {
            log.warn("Intento de acceso con ID inválido: {}", id);
            return ResponseEntity.badRequest().build();
        }

        log.info("Solicitando cliente con ID: {}", id);
        return clienteService.obtenerClientePorId(id)
                .map(cliente -> {
                    log.info("Cliente encontrado con ID: {}", id);
                    return ResponseEntity.ok(cliente);
                })
                .orElseGet(() -> {
                    log.warn("Cliente no encontrado con ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Crear nuevo cliente", description = "Crea un nuevo cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de cliente inválidos"),
            @ApiResponse(responseCode = "409", description = "Cliente ya existe")
    })
    @PostMapping
    public ResponseEntity<ClienteEntity> crearCliente(@Valid @RequestBody ClienteEntity cliente) {
        log.info("Creando nuevo cliente: {}", cliente.getNombre());
        ClienteEntity nuevoCliente = clienteService.crearCliente(cliente);
        log.info("Cliente creado exitosamente con ID: {}", nuevoCliente.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    @Operation(summary = "Actualizar cliente", description = "Actualiza un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteEntity> actualizarCliente(
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long id,
            @Valid @RequestBody ClienteEntity cliente) {

        if (id <= 0) {
            log.warn("Intento de actualización con ID inválido: {}", id);
            return ResponseEntity.badRequest().build();
        }

        log.info("Actualizando cliente con ID: {}", id);
        return clienteService.actualizarCliente(id, cliente)
                .map(clienteActualizado -> {
                    log.info("Cliente actualizado exitosamente con ID: {}", id);
                    return ResponseEntity.ok(clienteActualizado);
                })
                .orElseGet(() -> {
                    log.warn("Intento de actualizar cliente inexistente con ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long id) {

        if (id <= 0) {
            log.warn("Intento de eliminación con ID inválido: {}", id);
            return ResponseEntity.badRequest().build();
        }

        log.info("Eliminando cliente con ID: {}", id);
        boolean eliminado = clienteService.eliminarCliente(id);

        if (eliminado) {
            log.info("Cliente eliminado exitosamente con ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Intento de eliminar cliente inexistente con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar clientes por nombre", description = "Busca clientes que contengan el texto especificado en el nombre")
    @GetMapping("/buscar")
    public ResponseEntity<Page<ClienteEntity>> buscarClientesPorNombre(
            @Parameter(description = "Texto a buscar en el nombre") @RequestParam String nombre,
            Pageable pageable) {

        log.info("Buscando clientes con nombre que contiene: '{}'", nombre);
        Page<ClienteEntity> clientes = clienteService.buscarPorNombre(nombre, pageable);
        return ResponseEntity.ok(clientes);
    }
}
