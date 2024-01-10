package com.energia.enrique.facturacionservice.service;

import com.energia.enrique.facturacionservice.model.FacturaEntity;
import com.energia.enrique.facturacionservice.repository.FacturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;

    @Override
    public List<FacturaEntity> obtenerTodasLasFacturas() {
        return facturaRepository.findAll();
    }

    @Override
    public Optional<FacturaEntity> obtenerFacturaPorId(Long id) {
        return facturaRepository.findById(id);
    }

    @Override
    public FacturaEntity crearFactura(FacturaEntity nuevaFactura) {
        return facturaRepository.save(nuevaFactura);
    }

    @Override
    public Optional<FacturaEntity> actualizarFactura(Long id, FacturaEntity facturaActualizada) {
        return facturaRepository.findById(id)
                .map(factura -> {
                    // Realizar las actualizaciones necesarias en los atributos
                    // de la factura existente con los valores de la factura actualizada
                    factura.setNumeroFactura(facturaActualizada.getNumeroFactura());
                    factura.setNumeroTimbrado(facturaActualizada.getNumeroTimbrado());
                    // Actualizar otros atributos según sea necesario

                    // Guardar y devolver la factura actualizada
                    return facturaRepository.save(factura);
                });
    }

    @Override
    public void eliminarFactura(Long id) {
        facturaRepository.deleteById(id);
    }
}
