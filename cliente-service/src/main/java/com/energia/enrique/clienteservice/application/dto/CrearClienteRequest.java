package com.energia.enrique.clienteservice.application.dto;

import com.energia.enrique.clienteservice.domain.entities.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para solicitud de creación de cliente.
 * Parte de la capa de aplicación - Clean Architecture.
 * Contiene validaciones de entrada y formato.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearClienteRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener letras y espacios")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El apellido solo puede contener letras y espacios")
    private String apellido;

    @NotBlank(message = "La identificación es obligatoria")
    @Size(min = 5, max = 20, message = "La identificación debe tener entre 5 y 20 caracteres")
    private String identificacion;

    @NotNull(message = "El tipo de identificación es obligatorio")
    private Cliente.TipoIdentificacion tipoIdentificacion;

    @NotNull(message = "El tipo de cliente es obligatorio")
    private Cliente.TipoCliente tipoCliente;

    // Información de contacto
    @NotBlank(message = "El teléfono principal es obligatorio")
    @Pattern(regexp = "^[+]?[0-9]{8,15}$", message = "Formato de teléfono principal inválido")
    private String telefonoPrincipal;

    @Pattern(regexp = "^[+]?[0-9]{8,15}$", message = "Formato de teléfono secundario inválido")
    private String telefonoSecundario;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;

    @Email(message = "Formato de email secundario inválido")
    @Size(max = 100, message = "El email secundario no puede exceder 100 caracteres")
    private String emailSecundario;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 10, max = 200, message = "La dirección debe tener entre 10 y 200 caracteres")
    private String direccion;

    @Size(max = 50, message = "La ciudad no puede exceder 50 caracteres")
    private String ciudad;

    @Pattern(regexp = "^[0-9]{4,10}$", message = "Formato de código postal inválido")
    private String codigoPostal;

    @Size(max = 50, message = "El país no puede exceder 50 caracteres")
    private String pais;

    // Información opcional
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;

    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    private String observaciones;

    // Métodos de utilidad para validaciones adicionales
    public boolean esEmailValido() {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public boolean esTelefonoValido() {
        return telefonoPrincipal != null && telefonoPrincipal.matches("^[+]?[0-9]{8,15}$");
    }

    public boolean esMayorDeEdad() {
        if (fechaNacimiento == null) {
            return true; // Si no especifica fecha, asumimos que es mayor
        }
        return fechaNacimiento.isBefore(LocalDate.now().minusYears(18));
    }

    public int getEdadCalculada() {
        if (fechaNacimiento == null) {
            return 0;
        }
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }

    @Override
    public String toString() {
        return String.format("CrearClienteRequest{nombre='%s', apellido='%s', identificacion='%s', tipo='%s'}",
                           nombre, apellido, identificacion, tipoCliente);
    }
}