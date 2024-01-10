package com.energia.enrique.medidorservice.repository;

import com.energia.enrique.medidorservice.model.MedidorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedidorRepository extends JpaRepository<MedidorEntity,Long> {
}
