package com.energia.enrique.clienteservice.domain.entities;

import com.energia.shared.domain.entities.BaseEntity;
import com.energia.shared.domain.valueobjects.ContactoInfo;
import com.energia.shared.domain.exceptions.DomainValidationException;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entidad de dominio Cliente - Representa un cliente del sistema energético.
 * Implementa Clean Architecture con lógica de negocio encapsulada.
 */
public class Cliente extends BaseEntity {

    private String nombre;
    private String apellido;
    private String identificacion;
    private TipoIdentificacion tipoIdentificacion;
    private TipoCliente tipoCliente;
    private ContactoInfo contactoInfo;
    private LocalDate fechaNacimiento;
    private EstadoCliente estado;
    private String observaciones;

    // Constructor protegido para evitar creación directa
    protected Cliente() {
        super();
        this.estado = EstadoCliente.ACTIVO;
    }

    // Constructor para creación con Builder
    private Cliente(Builder builder) {
        super();
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.identificacion = builder.identificacion;
        this.tipoIdentificacion = builder.tipoIdentificacion;
        this.tipoCliente = builder.tipoCliente;
        this.contactoInfo = builder.contactoInfo;
        this.fechaNacimiento = builder.fechaNacimiento;
        this.observaciones = builder.observaciones;
        this.estado = EstadoCliente.ACTIVO;

        validarReglasDeNegocio();
    }

    public static Builder builder() {
        return new Builder();
    }

    // Método de fábrica para crear cliente
    public static Cliente crear(String nombre, String apellido, String identificacion,
                               TipoIdentificacion tipoIdentificacion, TipoCliente tipoCliente,
                               ContactoInfo contactoInfo) {
        return Cliente.builder()
                .nombre(nombre)
                .apellido(apellido)
                .identificacion(identificacion)
                .tipoIdentificacion(tipoIdentificacion)
                .tipoCliente(tipoCliente)
                .contactoInfo(contactoInfo)
                .build();
    }

    // Validaciones de reglas de negocio
    private void validarReglasDeNegocio() {
        validarNombre();
        validarApellido();
        validarIdentificacion();
        validarTipoIdentificacion();
        validarTipoCliente();
        validarContactoInfo();
        validarFechaNacimiento();
    }

    private void validarNombre() {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DomainValidationException("El nombre es obligatorio", "nombre");
        }
        if (nombre.length() < 2 || nombre.length() > 50) {
            throw new DomainValidationException("El nombre debe tener entre 2 y 50 caracteres", "nombre");
        }
    }

    private void validarApellido() {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new DomainValidationException("El apellido es obligatorio", "apellido");
        }
        if (apellido.length() < 2 || apellido.length() > 50) {
            throw new DomainValidationException("El apellido debe tener entre 2 y 50 caracteres", "apellido");
        }
    }

    private void validarIdentificacion() {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new DomainValidationException("La identificación es obligatoria", "identificacion");
        }
        if (identificacion.length() < 5 || identificacion.length() > 20) {
            throw new DomainValidationException("La identificación debe tener entre 5 y 20 caracteres", "identificacion");
        }
    }

    private void validarTipoIdentificacion() {
        if (tipoIdentificacion == null) {
            throw new DomainValidationException("El tipo de identificación es obligatorio", "tipoIdentificacion");
        }
    }

    private void validarTipoCliente() {
        if (tipoCliente == null) {
            throw new DomainValidationException("El tipo de cliente es obligatorio", "tipoCliente");
        }
    }

    private void validarContactoInfo() {
        if (contactoInfo == null) {
            throw new DomainValidationException("La información de contacto es obligatoria", "contactoInfo");
        }
    }

    private void validarFechaNacimiento() {
        if (fechaNacimiento != null) {
            LocalDate fechaMinima = LocalDate.now().minusYears(120);
            LocalDate fechaMaxima = LocalDate.now().minusYears(18);

            if (fechaNacimiento.isBefore(fechaMinima)) {
                throw new DomainValidationException("La fecha de nacimiento no puede ser anterior a 120 años", "fechaNacimiento");
            }

            if (fechaNacimiento.isAfter(fechaMaxima)) {
                throw new DomainValidationException("El cliente debe ser mayor de 18 años", "fechaNacimiento");
            }
        }
    }

    // Métodos de comportamiento de negocio
    public void actualizarInformacionPersonal(String nombre, String apellido) {
        String nombreAnterior = this.nombre;
        String apellidoAnterior = this.apellido;

        this.nombre = nombre;
        this.apellido = apellido;

        try {
            validarNombre();
            validarApellido();
            marcarComoActualizado("SYSTEM"); // En un caso real vendría del contexto
        } catch (DomainValidationException e) {
            // Revertir cambios en caso de error
            this.nombre = nombreAnterior;
            this.apellido = apellidoAnterior;
            throw e;
        }
    }

    public void actualizarContacto(ContactoInfo nuevoContacto) {
        ContactoInfo contactoAnterior = this.contactoInfo;
        this.contactoInfo = nuevoContacto;

        try {
            validarContactoInfo();
            marcarComoActualizado("SYSTEM");
        } catch (DomainValidationException e) {
            this.contactoInfo = contactoAnterior;
            throw e;
        }
    }

    public void cambiarTipoCliente(TipoCliente nuevoTipo) {
        if (nuevoTipo == null) {
            throw new DomainValidationException("El nuevo tipo de cliente no puede ser nulo", "tipoCliente");
        }

        if (this.tipoCliente == nuevoTipo) {
            return; // No hay cambio
        }

        this.tipoCliente = nuevoTipo;
        marcarComoActualizado("SYSTEM");
    }

    public void suspender(String motivo) {
        if (this.estado == EstadoCliente.SUSPENDIDO) {
            throw new DomainValidationException("El cliente ya está suspendido");
        }

        this.estado = EstadoCliente.SUSPENDIDO;
        this.observaciones = "SUSPENDIDO: " + (motivo != null ? motivo : "Sin motivo especificado");
        marcarComoActualizado("SYSTEM");
    }

    public void reactivar() {
        if (this.estado == EstadoCliente.ACTIVO) {
            throw new DomainValidationException("El cliente ya está activo");
        }

        this.estado = EstadoCliente.ACTIVO;
        this.observaciones = "Cliente reactivado en fecha: " + java.time.LocalDateTime.now();
        marcarComoActualizado("SYSTEM");
    }

    public void eliminar() {
        this.estado = EstadoCliente.ELIMINADO;
        desactivar();
        marcarComoActualizado("SYSTEM");
    }

    public String getNombreCompleto() {
        return String.format("%s %s", nombre, apellido);
    }

    public boolean esMayorDeEdad() {
        if (fechaNacimiento == null) {
            return true; // Asumimos que es mayor si no especifica fecha
        }
        return fechaNacimiento.isBefore(LocalDate.now().minusYears(18));
    }

    public boolean puedeRealizarTransacciones() {
        return estado == EstadoCliente.ACTIVO && isActivo();
    }

    public int getEdad() {
        if (fechaNacimiento == null) {
            return 0;
        }
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getIdentificacion() { return identificacion; }
    public TipoIdentificacion getTipoIdentificacion() { return tipoIdentificacion; }
    public TipoCliente getTipoCliente() { return tipoCliente; }
    public ContactoInfo getContactoInfo() { return contactoInfo; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public EstadoCliente getEstado() { return estado; }
    public String getObservaciones() { return observaciones; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(identificacion, cliente.identificacion) &&
               tipoIdentificacion == cliente.tipoIdentificacion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), identificacion, tipoIdentificacion);
    }

    @Override
    public String toString() {
        return String.format("Cliente{id='%s', nombre='%s', apellido='%s', identificacion='%s', tipo='%s', estado='%s'}",
                           getId(), nombre, apellido, identificacion, tipoCliente, estado);
    }

    // Builder Pattern
    public static class Builder {
        private String nombre;
        private String apellido;
        private String identificacion;
        private TipoIdentificacion tipoIdentificacion;
        private TipoCliente tipoCliente;
        private ContactoInfo contactoInfo;
        private LocalDate fechaNacimiento;
        private String observaciones;

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder apellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public Builder identificacion(String identificacion) {
            this.identificacion = identificacion;
            return this;
        }

        public Builder tipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
            this.tipoIdentificacion = tipoIdentificacion;
            return this;
        }

        public Builder tipoCliente(TipoCliente tipoCliente) {
            this.tipoCliente = tipoCliente;
            return this;
        }

        public Builder contactoInfo(ContactoInfo contactoInfo) {
            this.contactoInfo = contactoInfo;
            return this;
        }

        public Builder fechaNacimiento(LocalDate fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
            return this;
        }

        public Builder observaciones(String observaciones) {
            this.observaciones = observaciones;
            return this;
        }

        public Cliente build() {
            return new Cliente(this);
        }
    }

    // Enums
    public enum TipoIdentificacion {
        CEDULA("Cédula de Ciudadanía"),
        PASAPORTE("Pasaporte"),
        RUC("RUC"),
        EXTRANJERIA("Cédula de Extranjería");

        private final String descripcion;

        TipoIdentificacion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() { return descripcion; }
    }

    public enum TipoCliente {
        RESIDENCIAL("Cliente Residencial"),
        COMERCIAL("Cliente Comercial"),
        INDUSTRIAL("Cliente Industrial"),
        GUBERNAMENTAL("Cliente Gubernamental");

        private final String descripcion;

        TipoCliente(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() { return descripcion; }
    }

    public enum EstadoCliente {
        ACTIVO("Cliente Activo"),
        SUSPENDIDO("Cliente Suspendido"),
        ELIMINADO("Cliente Eliminado");

        private final String descripcion;

        EstadoCliente(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() { return descripcion; }
    }
}