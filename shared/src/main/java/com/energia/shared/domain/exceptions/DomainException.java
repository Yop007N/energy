package com.energia.shared.domain.exceptions;

/**
 * Excepción base para todas las excepciones del dominio.
 * Parte de la Clean Architecture - manejo de errores de negocio.
 */
public abstract class DomainException extends RuntimeException {

    private final String codigoError;

    protected DomainException(String mensaje) {
        super(mensaje);
        this.codigoError = this.getClass().getSimpleName().replace("Exception", "").toUpperCase();
    }

    protected DomainException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.codigoError = this.getClass().getSimpleName().replace("Exception", "").toUpperCase();
    }

    protected DomainException(String mensaje, String codigoError) {
        super(mensaje);
        this.codigoError = codigoError;
    }

    protected DomainException(String mensaje, String codigoError, Throwable causa) {
        super(mensaje, causa);
        this.codigoError = codigoError;
    }

    public String getCodigoError() {
        return codigoError;
    }

    @Override
    public String toString() {
        return String.format("%s{codigo='%s', mensaje='%s'}",
                           getClass().getSimpleName(), codigoError, getMessage());
    }
}