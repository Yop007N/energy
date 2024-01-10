package com.energia.enrique.contratoservice.service;

import com.energia.enrique.contratoservice.model.ContratoEntity;
import com.energia.enrique.contratoservice.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

@Service
public class ContratoServiceImpl implements ContratoService {

    private final ContratoRepository contratoRepository;

    @Autowired
    public ContratoServiceImpl(ContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    @Override
    public List<ContratoEntity> obtenerTodosContratos() {
        return contratoRepository.findAll();
    }

    @Override
    public Optional<ContratoEntity> obtenerContratoPorId(Long id) {
        return contratoRepository.findById(id);
    }

    @Override
    public ContratoEntity crearContrato(ContratoEntity contrato) {
        return contratoRepository.save(contrato);
    }

    @Override
    public ContratoEntity actualizarContrato(Long id, ContratoEntity nuevoContrato) {
        return contratoRepository.findById(id)
                .map(contratoExistente -> {
                    contratoExistente.setNumeroContrato(nuevoContrato.getNumeroContrato());
                    contratoExistente.setTitularContrato(nuevoContrato.getTitularContrato());
                    contratoExistente.setDireccionSuministro(nuevoContrato.getDireccionSuministro());
                    contratoExistente.setCicloFacturacion(nuevoContrato.getCicloFacturacion());
                    contratoExistente.setIdCliente(nuevoContrato.getIdCliente());

                    return contratoRepository.save(contratoExistente);
                })
                .orElseThrow(() -> new NoSuchElementException("Contrato no encontrado con ID: " + id));
    }

    @Override
    public void eliminarContrato(Long id) {
        contratoRepository.deleteById(id);
    }
}
