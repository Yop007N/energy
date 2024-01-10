package com.energia.enrique.medidorservice.service;

import com.energia.enrique.medidorservice.model.MedidorEntity;

import java.util.List;
import java.util.Optional;

public interface MedidorService {
    List<MedidorEntity> obtenerTodosMedidores();

    Optional<MedidorEntity> obtenerMedidorPorId(Long id);

    MedidorEntity crearMedidor(MedidorEntity medidor);

    MedidorEntity actualizarMedidor(Long id, MedidorEntity nuevoMedidor);

    void eliminarMedidor(Long id);
}
