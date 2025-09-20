package com.energia.enrique.clienteservice.application.dto;

import com.energia.enrique.clienteservice.domain.entities.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para operaciones con Cliente.
 * Parte de la capa de aplicación - Clean Architecture.
 * Proporciona una vista completa y estructurada del cliente.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    // Información básica
    private String id;
    private String nombre;
    private String apellido;
    private String nombreCompleto;
    private String identificacion;
    private Cliente.TipoIdentificacion tipoIdentificacion;
    private String tipoIdentificacionDescripcion;
    private Cliente.TipoCliente tipoCliente;
    private String tipoClienteDescripcion;

    // Información de contacto
    private String telefonoPrincipal;
    private String telefonoSecundario;
    private String email;
    private String emailSecundario;
    private String direccion;
    private String direccionCompleta;
    private String ciudad;
    private String codigoPostal;
    private String pais;

    // Información personal
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    private Integer edad;
    private Boolean esMayorDeEdad;

    // Estado y control
    private Cliente.EstadoCliente estado;
    private String estadoDescripcion;
    private String observaciones;
    private Boolean puedeRealizarTransacciones;
    private Boolean activo;

    // Auditoría
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaCreacion;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaActualizacion;
    private String creadoPor;
    private String actualizadoPor;
    private Integer version;

    // Información calculada y de conveniencia
    private Boolean tieneContactoSecundario;
    private Boolean tieneEmailSecundario;
    private String resumenContacto;

    // Métodos post-construcción para enriquecer datos
    public void enriquecerInformacion() {
        // Agregar descripciones de enums
        if (tipoIdentificacion != null) {
            this.tipoIdentificacionDescripcion = tipoIdentificacion.getDescripcion();
        }

        if (tipoCliente != null) {
            this.tipoClienteDescripcion = tipoCliente.getDescripcion();
        }

        if (estado != null) {
            this.estadoDescripcion = estado.getDescripcion();
        }

        // Calcular información de contacto
        this.tieneContactoSecundario = telefonoSecundario != null && !telefonoSecundario.trim().isEmpty();
        this.tieneEmailSecundario = emailSecundario != null && !emailSecundario.trim().isEmpty();

        // Crear resumen de contacto
        StringBuilder resumen = new StringBuilder();
        resumen.append("Tel: ").append(telefonoPrincipal);
        if (tieneContactoSecundario) {
            resumen.append(" / ").append(telefonoSecundario);
        }
        resumen.append(" | Email: ").append(email);
        if (tieneEmailSecundario) {
            resumen.append(" / ").append(emailSecundario);
        }
        this.resumenContacto = resumen.toString();
    }

    /**
     * Método estático para crear respuesta desde entidad de dominio.
     */
    public static ClienteResponse fromDomain(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteResponse response = ClienteResponse.builder()
            .id(cliente.getId())
            .nombre(cliente.getNombre())
            .apellido(cliente.getApellido())
            .nombreCompleto(cliente.getNombreCompleto())
            .identificacion(cliente.getIdentificacion())
            .tipoIdentificacion(cliente.getTipoIdentificacion())
            .tipoCliente(cliente.getTipoCliente())
            .fechaNacimiento(cliente.getFechaNacimiento())
            .edad(cliente.getEdad())
            .esMayorDeEdad(cliente.esMayorDeEdad())
            .estado(cliente.getEstado())
            .observaciones(cliente.getObservaciones())
            .puedeRealizarTransacciones(cliente.puedeRealizarTransacciones())
            .activo(cliente.isActivo())
            .fechaCreacion(cliente.getFechaCreacion())
            .fechaActualizacion(cliente.getFechaActualizacion())
            .creadoPor(cliente.getCreadoPor())
            .actualizadoPor(cliente.getActualizadoPor())
            .version(cliente.getVersion())
            .build();

        // Mapear información de contacto si existe
        if (cliente.getContactoInfo() != null) {
            response.setTelefonoPrincipal(cliente.getContactoInfo().getTelefonoPrincipal());
            response.setTelefonoSecundario(cliente.getContactoInfo().getTelefonoSecundario());
            response.setEmail(cliente.getContactoInfo().getEmail());
            response.setEmailSecundario(cliente.getContactoInfo().getEmailSecundario());
            response.setDireccion(cliente.getContactoInfo().getDireccion());
            response.setDireccionCompleta(cliente.getContactoInfo().getDireccionCompleta());
            response.setCiudad(cliente.getContactoInfo().getCiudad());
            response.setCodigoPostal(cliente.getContactoInfo().getCodigoPostal());
            response.setPais(cliente.getContactoInfo().getPais());
        }

        // Enriquecer información adicional
        response.enriquecerInformacion();

        return response;
    }

    @Override
    public String toString() {
        return String.format("ClienteResponse{id='%s', nombreCompleto='%s', identificacion='%s', tipo='%s', estado='%s'}",
                           id, nombreCompleto, identificacion, tipoCliente, estado);
    }
}