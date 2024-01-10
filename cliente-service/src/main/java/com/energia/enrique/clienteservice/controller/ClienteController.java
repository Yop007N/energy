package com.energia.enrique.clienteservice.controller;

import com.energia.enrique.clienteservice.model.ClienteEntity;
import com.energia.enrique.clienteservice.service.ClienteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteEntity>> obtenerTodosClientes() {
        try {
            List<ClienteEntity> clientes = clienteService.obtenerTodosClientes();
            return new ResponseEntity<>(clientes, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al obtener todos los clientes", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> obtenerClientePorId(@PathVariable Long id) {
        try {
            Optional<ClienteEntity> cliente = clienteService.obtenerClientePorId(id);
            return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error al obtener el cliente con ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ClienteEntity> crearCliente(@Valid @RequestBody ClienteEntity cliente) {
        try {
            ClienteEntity nuevoCliente = clienteService.crearCliente(cliente);
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error al crear un nuevo cliente", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteEntity> actualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteEntity cliente) {
        try {
            ClienteEntity clienteActualizado = clienteService.actualizarCliente(id, cliente);
            if (clienteActualizado != null) {
                return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error al actualizar el cliente con ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        try {
            clienteService.eliminarCliente(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            logger.error("Error al eliminar el cliente con ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
