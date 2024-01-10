package com.energia.enrique.medidorservice.controller;

import com.energia.enrique.medidorservice.model.MedidorEntity;
import com.energia.enrique.medidorservice.service.MedidorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medidores")
@Validated
@RequiredArgsConstructor
public class MedidorController {

    private final MedidorService medidorService;
    private final Logger logger = LoggerFactory.getLogger(MedidorController.class);

    @GetMapping
    public ResponseEntity<List<MedidorEntity>> obtenerTodosMedidores() {
        List<MedidorEntity> medidores = medidorService.obtenerTodosMedidores();
        logger.info("Obteniendo todos los medidores. Cantidad: {}", medidores.size());
        return ResponseEntity.ok(medidores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedidorEntity> obtenerMedidorPorId(@PathVariable Long id) {
        return medidorService.obtenerMedidorPorId(id)
                .map(medidor -> {
                    logger.info("Obteniendo medidor con ID: {}", id);
                    return ResponseEntity.ok(medidor);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MedidorEntity> crearMedidor(@RequestBody @Valid MedidorEntity medidor) {
        MedidorEntity nuevoMedidor = medidorService.crearMedidor(medidor);
        logger.info("Creando nuevo medidor. ID: {}", nuevoMedidor.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMedidor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedidorEntity> actualizarMedidor(@PathVariable Long id, @RequestBody @Valid MedidorEntity nuevoMedidor) {
        MedidorEntity medidorActualizado = medidorService.actualizarMedidor(id, nuevoMedidor);

        if (medidorActualizado != null) {
            logger.info("Actualizando medidor con ID: {}", id);
            return ResponseEntity.ok(medidorActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedidor(@PathVariable Long id) {
        medidorService.eliminarMedidor(id);
        logger.info("Eliminando medidor con ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error("Ocurrió un error inesperado: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado. Consulta los registros para más detalles.");
    }
}
