package com.energia.enrique.facturacionservice.controller;

import com.energia.enrique.facturacionservice.model.FacturaEntity;
import com.energia.enrique.facturacionservice.service.FacturaService;
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
@RequestMapping("/facturas")
@Validated
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;
    private final Logger logger = LoggerFactory.getLogger(FacturaController.class);

    @GetMapping
    public ResponseEntity<List<FacturaEntity>> obtenerTodasLasFacturas() {
        List<FacturaEntity> facturas = facturaService.obtenerTodasLasFacturas();
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaEntity> obtenerFacturaPorId(@PathVariable Long id) {
        return facturaService.obtenerFacturaPorId(id)
                .map(factura -> {
                    logger.info("Obteniendo factura con ID: {}", id);
                    return ResponseEntity.ok(factura);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FacturaEntity> crearFactura(@RequestBody @Valid FacturaEntity nuevaFactura) {
        FacturaEntity facturaCreada = facturaService.crearFactura(nuevaFactura);
        logger.info("Factura creada con éxito. Número de Factura: {}", facturaCreada.getNumeroFactura());
        return ResponseEntity.status(HttpStatus.CREATED).body(facturaCreada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacturaEntity> actualizarFactura(@PathVariable Long id, @RequestBody @Valid FacturaEntity facturaActualizada) {
        return facturaService.actualizarFactura(id, facturaActualizada)
                .map(factura -> {
                    logger.info("Actualizando factura con ID: {}", id);
                    return ResponseEntity.ok(factura);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
        logger.info("Factura eliminada con éxito. ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
