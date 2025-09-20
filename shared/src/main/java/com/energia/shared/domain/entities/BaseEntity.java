package com.energia.shared.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad base que proporciona propiedades comunes para todas las entidades del dominio.
 * Implementa Clean Architecture separando la lógica de persistencia de la lógica de negocio.
 */
public abstract class BaseEntity {

    protected String id;
    protected LocalDateTime fechaCreacion;
    protected LocalDateTime fechaActualizacion;
    protected String creadoPor;
    protected String actualizadoPor;
    protected boolean activo;
    protected Integer version;

    protected BaseEntity() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.activo = true;
        this.version = 0;
    }

    protected BaseEntity(String id) {
        this();
        this.id = id;
    }

    // Getters
    public String getId() {
        return id;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public String getActualizadoPor() {
        return actualizadoPor;
    }

    public boolean isActivo() {
        return activo;
    }

    public Integer getVersion() {
        return version;
    }

    // Métodos de negocio
    public void marcarComoCreado(String usuario) {
        this.creadoPor = usuario;
        this.fechaCreacion = LocalDateTime.now();
    }

    public void marcarComoActualizado(String usuario) {
        this.actualizadoPor = usuario;
        this.fechaActualizacion = LocalDateTime.now();
        this.version++;
    }

    public void activar() {
        this.activo = true;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void desactivar() {
        this.activo = false;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public boolean esNuevo() {
        return this.id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s{id='%s', activo=%s, version=%d}",
                           getClass().getSimpleName(), id, activo, version);
    }
}