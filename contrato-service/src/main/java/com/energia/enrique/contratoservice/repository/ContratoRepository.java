package com.energia.enrique.contratoservice.repository;

import com.energia.enrique.contratoservice.model.ContratoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepository extends JpaRepository<ContratoEntity,Long> {
}
