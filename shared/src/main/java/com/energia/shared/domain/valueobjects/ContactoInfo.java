package com.energia.shared.domain.valueobjects;

import com.energia.shared.domain.exceptions.DomainValidationException;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object que encapsula la información de contacto.
 * Immutable y con validaciones de dominio incorporadas.
 */
public final class ContactoInfo {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern TELEFONO_PATTERN =
        Pattern.compile("^[+]?[0-9]{8,15}$");

    private final String telefonoPrincipal;
    private final String telefonoSecundario;
    private final String email;
    private final String emailSecundario;
    private final String direccion;
    private final String ciudad;
    private final String codigoPostal;
    private final String pais;

    private ContactoInfo(Builder builder) {
        this.telefonoPrincipal = builder.telefonoPrincipal;
        this.telefonoSecundario = builder.telefonoSecundario;
        this.email = builder.email;
        this.emailSecundario = builder.emailSecundario;
        this.direccion = builder.direccion;
        this.ciudad = builder.ciudad;
        this.codigoPostal = builder.codigoPostal;
        this.pais = builder.pais;

        validar();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void validar() {
        if (telefonoPrincipal == null || telefonoPrincipal.trim().isEmpty()) {
            throw new DomainValidationException("El teléfono principal es obligatorio");
        }

        if (!TELEFONO_PATTERN.matcher(telefonoPrincipal).matches()) {
            throw new DomainValidationException("Formato de teléfono principal inválido");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new DomainValidationException("El email es obligatorio");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new DomainValidationException("Formato de email inválido");
        }

        if (telefonoSecundario != null && !telefonoSecundario.trim().isEmpty()) {
            if (!TELEFONO_PATTERN.matcher(telefonoSecundario).matches()) {
                throw new DomainValidationException("Formato de teléfono secundario inválido");
            }
        }

        if (emailSecundario != null && !emailSecundario.trim().isEmpty()) {
            if (!EMAIL_PATTERN.matcher(emailSecundario).matches()) {
                throw new DomainValidationException("Formato de email secundario inválido");
            }
        }

        if (direccion == null || direccion.trim().isEmpty()) {
            throw new DomainValidationException("La dirección es obligatoria");
        }
    }

    // Getters
    public String getTelefonoPrincipal() { return telefonoPrincipal; }
    public String getTelefonoSecundario() { return telefonoSecundario; }
    public String getEmail() { return email; }
    public String getEmailSecundario() { return emailSecundario; }
    public String getDireccion() { return direccion; }
    public String getCiudad() { return ciudad; }
    public String getCodigoPostal() { return codigoPostal; }
    public String getPais() { return pais; }

    // Métodos de comportamiento
    public boolean tieneContactoSecundario() {
        return telefonoSecundario != null && !telefonoSecundario.trim().isEmpty();
    }

    public boolean tieneEmailSecundario() {
        return emailSecundario != null && !emailSecundario.trim().isEmpty();
    }

    public String getDireccionCompleta() {
        StringBuilder direccionCompleta = new StringBuilder(direccion);

        if (ciudad != null && !ciudad.trim().isEmpty()) {
            direccionCompleta.append(", ").append(ciudad);
        }

        if (codigoPostal != null && !codigoPostal.trim().isEmpty()) {
            direccionCompleta.append(" ").append(codigoPostal);
        }

        if (pais != null && !pais.trim().isEmpty()) {
            direccionCompleta.append(", ").append(pais);
        }

        return direccionCompleta.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactoInfo that = (ContactoInfo) o;
        return Objects.equals(telefonoPrincipal, that.telefonoPrincipal) &&
               Objects.equals(email, that.email) &&
               Objects.equals(direccion, that.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(telefonoPrincipal, email, direccion);
    }

    @Override
    public String toString() {
        return String.format("ContactoInfo{telefono='%s', email='%s', direccion='%s'}",
                           telefonoPrincipal, email, direccion);
    }

    // Builder Pattern
    public static class Builder {
        private String telefonoPrincipal;
        private String telefonoSecundario;
        private String email;
        private String emailSecundario;
        private String direccion;
        private String ciudad;
        private String codigoPostal;
        private String pais;

        public Builder telefonoPrincipal(String telefonoPrincipal) {
            this.telefonoPrincipal = telefonoPrincipal;
            return this;
        }

        public Builder telefonoSecundario(String telefonoSecundario) {
            this.telefonoSecundario = telefonoSecundario;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder emailSecundario(String emailSecundario) {
            this.emailSecundario = emailSecundario;
            return this;
        }

        public Builder direccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder ciudad(String ciudad) {
            this.ciudad = ciudad;
            return this;
        }

        public Builder codigoPostal(String codigoPostal) {
            this.codigoPostal = codigoPostal;
            return this;
        }

        public Builder pais(String pais) {
            this.pais = pais;
            return this;
        }

        public ContactoInfo build() {
            return new ContactoInfo(this);
        }
    }
}