package com.energia.enrique.clienteservice.service;

import com.energia.enrique.clienteservice.model.ClienteEntity;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<ClienteEntity> obtenerTodosClientes();

    Optional<ClienteEntity> obtenerClientePorId(Long id);

    ClienteEntity crearCliente(ClienteEntity cliente);

    ClienteEntity actualizarCliente(Long id, ClienteEntity cliente);

    void eliminarCliente(Long id);
}
