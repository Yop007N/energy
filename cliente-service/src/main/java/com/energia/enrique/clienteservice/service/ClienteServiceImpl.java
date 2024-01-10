package com.energia.enrique.clienteservice.service;

import com.energia.enrique.clienteservice.model.ClienteEntity;
import com.energia.enrique.clienteservice.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<ClienteEntity> obtenerTodosClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<ClienteEntity> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public ClienteEntity crearCliente(ClienteEntity cliente) {
        // Puedes agregar validaciones u lógica de negocio aquí antes de guardar
        return clienteRepository.save(cliente);
    }

    @Override
    public ClienteEntity actualizarCliente(Long id, ClienteEntity cliente) {
        // Verificar si el cliente existe antes de actualizar
        if (clienteRepository.existsById(id)) {
            // Puedes agregar validaciones u lógica de negocio aquí antes de actualizar
            cliente.setId(id);
            return clienteRepository.save(cliente);
        } else {
            // Manejar el caso en que el cliente no existe
            // Puedes lanzar una excepción, devolver un valor predeterminado, etc.
            return null;
        }
    }

    @Override
    public void eliminarCliente(Long id) {
        // Eliminar el cliente si existe
        clienteRepository.deleteById(id);
    }
}
