package com.energia.enrique.clienteservice.application.usecases;

import com.energia.enrique.clienteservice.application.dto.CrearClienteRequest;
import com.energia.enrique.clienteservice.application.dto.ClienteResponse;
import com.energia.enrique.clienteservice.domain.entities.Cliente;
import com.energia.enrique.clienteservice.domain.repositories.ClienteRepository;
import com.energia.shared.domain.valueobjects.ContactoInfo;
import com.energia.shared.domain.exceptions.DomainValidationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Caso de uso para crear un nuevo cliente.
 * Implementa Clean Architecture - Capa de aplicación.
 * Orquesta la lógica de negocio sin contener reglas de dominio.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CrearClienteUseCase {

    private final ClienteRepository clienteRepository;

    /**
     * Ejecuta el caso de uso de creación de cliente.
     * @param request Datos del cliente a crear
     * @return Respuesta con la información del cliente creado
     */
    @Transactional
    public ClienteResponse ejecutar(CrearClienteRequest request) {
        log.info("Iniciando creación de cliente con identificación: {}", request.getIdentificacion());

        try {
            // 1. Validar que no existe cliente con la misma identificación
            validarClienteNoExiste(request.getIdentificacion(), request.getTipoIdentificacion());

            // 2. Validar que no existe cliente con el mismo email
            validarEmailNoExiste(request.getEmail());

            // 3. Crear objeto de valor ContactoInfo
            ContactoInfo contactoInfo = crearContactoInfo(request);

            // 4. Crear entidad Cliente usando el dominio
            Cliente cliente = Cliente.crear(
                request.getNombre(),
                request.getApellido(),
                request.getIdentificacion(),
                request.getTipoIdentificacion(),
                request.getTipoCliente(),
                contactoInfo
            );

            // 5. Agregar información opcional
            if (request.getFechaNacimiento() != null) {
                cliente = Cliente.builder()
                    .nombre(request.getNombre())
                    .apellido(request.getApellido())
                    .identificacion(request.getIdentificacion())
                    .tipoIdentificacion(request.getTipoIdentificacion())
                    .tipoCliente(request.getTipoCliente())
                    .contactoInfo(contactoInfo)
                    .fechaNacimiento(request.getFechaNacimiento())
                    .observaciones(request.getObservaciones())
                    .build();
            }

            // 6. Marcar como creado
            cliente.marcarComoCreado("SYSTEM"); // En un caso real vendría del contexto de seguridad

            // 7. Persistir cliente
            Cliente clienteGuardado = clienteRepository.guardar(cliente);

            log.info("Cliente creado exitosamente con ID: {}", clienteGuardado.getId());

            // 8. Retornar respuesta
            return mapearAResponse(clienteGuardado);

        } catch (DomainValidationException e) {
            log.warn("Error de validación al crear cliente: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al crear cliente", e);
            throw new RuntimeException("Error interno al crear cliente", e);
        }
    }

    private void validarClienteNoExiste(String identificacion, Cliente.TipoIdentificacion tipoIdentificacion) {
        if (clienteRepository.existePorIdentificacion(identificacion, tipoIdentificacion)) {
            throw new DomainValidationException(
                String.format("Ya existe un cliente con identificación %s de tipo %s",
                            identificacion, tipoIdentificacion.getDescripcion()),
                "identificacion"
            );
        }
    }

    private void validarEmailNoExiste(String email) {
        if (clienteRepository.existePorEmail(email)) {
            throw new DomainValidationException(
                String.format("Ya existe un cliente con el email %s", email),
                "email"
            );
        }
    }

    private ContactoInfo crearContactoInfo(CrearClienteRequest request) {
        return ContactoInfo.builder()
            .telefonoPrincipal(request.getTelefonoPrincipal())
            .telefonoSecundario(request.getTelefonoSecundario())
            .email(request.getEmail())
            .emailSecundario(request.getEmailSecundario())
            .direccion(request.getDireccion())
            .ciudad(request.getCiudad())
            .codigoPostal(request.getCodigoPostal())
            .pais(request.getPais())
            .build();
    }

    private ClienteResponse mapearAResponse(Cliente cliente) {
        return ClienteResponse.builder()
            .id(cliente.getId())
            .nombre(cliente.getNombre())
            .apellido(cliente.getApellido())
            .nombreCompleto(cliente.getNombreCompleto())
            .identificacion(cliente.getIdentificacion())
            .tipoIdentificacion(cliente.getTipoIdentificacion())
            .tipoCliente(cliente.getTipoCliente())
            .telefonoPrincipal(cliente.getContactoInfo().getTelefonoPrincipal())
            .telefonoSecundario(cliente.getContactoInfo().getTelefonoSecundario())
            .email(cliente.getContactoInfo().getEmail())
            .emailSecundario(cliente.getContactoInfo().getEmailSecundario())
            .direccion(cliente.getContactoInfo().getDireccion())
            .direccionCompleta(cliente.getContactoInfo().getDireccionCompleta())
            .ciudad(cliente.getContactoInfo().getCiudad())
            .codigoPostal(cliente.getContactoInfo().getCodigoPostal())
            .pais(cliente.getContactoInfo().getPais())
            .fechaNacimiento(cliente.getFechaNacimiento())
            .edad(cliente.getEdad())
            .esMayorDeEdad(cliente.esMayorDeEdad())
            .estado(cliente.getEstado())
            .observaciones(cliente.getObservaciones())
            .puedeRealizarTransacciones(cliente.puedeRealizarTransacciones())
            .activo(cliente.isActivo())
            .fechaCreacion(cliente.getFechaCreacion())
            .fechaActualizacion(cliente.getFechaActualizacion())
            .version(cliente.getVersion())
            .build();
    }
}