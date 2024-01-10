package com.energia.enrique.facturacionservice.repository;

import com.energia.enrique.facturacionservice.model.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository <FacturaEntity,Long> {
}
