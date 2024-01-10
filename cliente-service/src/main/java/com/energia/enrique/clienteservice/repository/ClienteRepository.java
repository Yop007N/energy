package com.energia.enrique.clienteservice.repository;

import com.energia.enrique.clienteservice.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity,Long> {
}
