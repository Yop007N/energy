package com.energia.shared.domain.exceptions;

/**
 * Excepción que se lanza cuando se violan reglas de validación del dominio.
 * Parte de la Clean Architecture - representa errores de negocio.
 */
public class DomainValidationException extends DomainException {

    private final String campo;

    public DomainValidationException(String mensaje) {
        super(mensaje);
        this.campo = null;
    }

    public DomainValidationException(String mensaje, String campo) {
        super(mensaje);
        this.campo = campo;
    }

    public DomainValidationException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.campo = null;
    }

    public DomainValidationException(String mensaje, String campo, Throwable causa) {
        super(mensaje, causa);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }

    @Override
    public String toString() {
        if (campo != null) {
            return String.format("DomainValidationException{campo='%s', mensaje='%s'}", campo, getMessage());
        }
        return String.format("DomainValidationException{mensaje='%s'}", getMessage());
    }
}