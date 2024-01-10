package com.energia.enrique.facturacionservice.service;

import com.energia.enrique.facturacionservice.model.FacturaEntity;

import java.util.List;
import java.util.Optional;

public interface FacturaService {
    List<FacturaEntity> obtenerTodasLasFacturas();

    Optional<FacturaEntity> obtenerFacturaPorId(Long id);

    FacturaEntity crearFactura(FacturaEntity nuevaFactura);

    Optional<FacturaEntity> actualizarFactura(Long id, FacturaEntity facturaActualizada);

    void eliminarFactura(Long id);
}
