package com.energia.enrique.contratoservice.controller;

import com.energia.enrique.contratoservice.model.ContratoEntity;
import com.energia.enrique.contratoservice.service.ContratoService;
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
@RequestMapping("/contratos")
public class ContratoController {

    private static final Logger logger = LoggerFactory.getLogger(ContratoController.class);

    private final ContratoService contratoService;

    @Autowired
    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping
    public ResponseEntity<List<ContratoEntity>> obtenerTodosContratos() {
        logger.info("Obteniendo todos los contratos");
        List<ContratoEntity> contratos = contratoService.obtenerTodosContratos();
        logger.info("Contratos obtenidos: {}", contratos);
        return new ResponseEntity<>(contratos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratoEntity> obtenerContratoPorId(@PathVariable Long id) {
        logger.info("Obteniendo contrato por ID: {}", id);
        Optional<ContratoEntity> contrato = contratoService.obtenerContratoPorId(id);
        return contrato.map(value -> {
            logger.info("Contrato encontrado: {}", value);
            return new ResponseEntity<>(value, HttpStatus.OK);
        }).orElseGet(() -> {
            logger.info("Contrato no encontrado con ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @PostMapping
    public ResponseEntity<ContratoEntity> crearContrato(@Valid @RequestBody ContratoEntity contrato) {
        logger.info("Creando un nuevo contrato: {}", contrato);
        ContratoEntity nuevoContrato = contratoService.crearContrato(contrato);
        logger.info("Contrato creado: {}", nuevoContrato);
        return new ResponseEntity<>(nuevoContrato, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContratoEntity> actualizarContrato(@PathVariable Long id, @Valid @RequestBody ContratoEntity nuevoContrato) {
        try {
            logger.info("Actualizando contrato con ID: {}", id);
            ContratoEntity contratoActualizado = contratoService.actualizarContrato(id, nuevoContrato);
            logger.info("Contrato actualizado: {}", contratoActualizado);
            return new ResponseEntity<>(contratoActualizado, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al actualizar contrato con ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarContrato(@PathVariable Long id) {
        try {
            logger.info("Eliminando contrato con ID: {}", id);
            contratoService.eliminarContrato(id);
            logger.info("Contrato eliminado con éxito");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error al eliminar contrato con ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
