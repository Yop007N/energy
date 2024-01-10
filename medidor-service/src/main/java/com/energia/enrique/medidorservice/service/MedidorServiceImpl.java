package com.energia.enrique.medidorservice.service;

import com.energia.enrique.medidorservice.model.MedidorEntity;
import com.energia.enrique.medidorservice.repository.MedidorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedidorServiceImpl implements MedidorService {

    private final MedidorRepository medidorRepository;

    @Override
    public List<MedidorEntity> obtenerTodosMedidores() {
        return medidorRepository.findAll();
    }

    @Override
    public Optional<MedidorEntity> obtenerMedidorPorId(Long id) {
        return medidorRepository.findById(id);
    }

    @Override
    public MedidorEntity crearMedidor(MedidorEntity medidor) {
        return medidorRepository.save(medidor);
    }

    @Override
    public MedidorEntity actualizarMedidor(Long id, MedidorEntity nuevoMedidor) {
        return medidorRepository.findById(id)
                .map(medidorExistente -> {
                    medidorExistente.setNumeroMedidor(nuevoMedidor.getNumeroMedidor());
                    medidorExistente.setTipoMedidor(nuevoMedidor.getTipoMedidor());
                    medidorExistente.setIdContrato(nuevoMedidor.getIdContrato());

                    return medidorRepository.save(medidorExistente);
                })
                .orElse(null);
    }

    @Override
    public void eliminarMedidor(Long id) {
        medidorRepository.deleteById(id);
    }
}
