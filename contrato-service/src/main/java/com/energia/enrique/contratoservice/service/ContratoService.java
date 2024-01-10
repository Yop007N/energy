package com.energia.enrique.contratoservice.service;

import com.energia.enrique.contratoservice.model.ContratoEntity;

import java.util.List;
import java.util.Optional;

public interface ContratoService {

    List<ContratoEntity> obtenerTodosContratos();

    Optional<ContratoEntity> obtenerContratoPorId(Long id);

    ContratoEntity crearContrato(ContratoEntity contrato);

    ContratoEntity actualizarContrato(Long id, ContratoEntity nuevoContrato);

    void eliminarContrato(Long id);
}
