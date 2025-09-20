package com.energia.enrique.clienteservice.domain.repositories;

import com.energia.enrique.clienteservice.domain.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz del repositorio de Cliente - Parte del dominio.
 * Define el contrato para la persistencia sin acoplarse a la implementación.
 * Principio de Inversión de Dependencias de SOLID.
 */
public interface ClienteRepository {

    /**
     * Guarda un cliente nuevo o actualiza uno existente.
     * @param cliente Cliente a guardar
     * @return Cliente guardado con ID asignado
     */
    Cliente guardar(Cliente cliente);

    /**
     * Busca un cliente por su ID.
     * @param id ID del cliente
     * @return Optional con el cliente encontrado o vacío si no existe
     */
    Optional<Cliente> buscarPorId(String id);

    /**
     * Busca un cliente por su identificación y tipo.
     * @param identificacion Número de identificación
     * @param tipoIdentificacion Tipo de identificación
     * @return Optional con el cliente encontrado o vacío si no existe
     */
    Optional<Cliente> buscarPorIdentificacion(String identificacion,
                                            Cliente.TipoIdentificacion tipoIdentificacion);

    /**
     * Busca clientes por email.
     * @param email Email de contacto
     * @return Lista de clientes con ese email
     */
    List<Cliente> buscarPorEmail(String email);

    /**
     * Busca clientes por tipo.
     * @param tipoCliente Tipo de cliente a buscar
     * @return Lista de clientes del tipo especificado
     */
    List<Cliente> buscarPorTipo(Cliente.TipoCliente tipoCliente);

    /**
     * Busca clientes por estado.
     * @param estado Estado del cliente
     * @return Lista de clientes en el estado especificado
     */
    List<Cliente> buscarPorEstado(Cliente.EstadoCliente estado);

    /**
     * Busca clientes activos (estado ACTIVO y activo = true).
     * @return Lista de clientes activos
     */
    List<Cliente> buscarActivos();

    /**
     * Busca todos los clientes con paginación.
     * @param pageable Información de paginación
     * @return Página con los clientes encontrados
     */
    Page<Cliente> buscarTodos(Pageable pageable);

    /**
     * Busca clientes por nombre o apellido (búsqueda parcial).
     * @param termino Término de búsqueda
     * @param pageable Información de paginación
     * @return Página con los clientes encontrados
     */
    Page<Cliente> buscarPorNombre(String termino, Pageable pageable);

    /**
     * Busca clientes por ciudad.
     * @param ciudad Ciudad de residencia
     * @return Lista de clientes en la ciudad especificada
     */
    List<Cliente> buscarPorCiudad(String ciudad);

    /**
     * Busca clientes por teléfono.
     * @param telefono Número de teléfono
     * @return Lista de clientes con ese teléfono
     */
    List<Cliente> buscarPorTelefono(String telefono);

    /**
     * Verifica si existe un cliente con la identificación dada.
     * @param identificacion Número de identificación
     * @param tipoIdentificacion Tipo de identificación
     * @return true si existe, false en caso contrario
     */
    boolean existePorIdentificacion(String identificacion,
                                  Cliente.TipoIdentificacion tipoIdentificacion);

    /**
     * Verifica si existe un cliente con el email dado.
     * @param email Email a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existePorEmail(String email);

    /**
     * Cuenta el total de clientes.
     * @return Número total de clientes
     */
    long contar();

    /**
     * Cuenta clientes por tipo.
     * @param tipoCliente Tipo de cliente
     * @return Número de clientes del tipo especificado
     */
    long contarPorTipo(Cliente.TipoCliente tipoCliente);

    /**
     * Cuenta clientes por estado.
     * @param estado Estado del cliente
     * @return Número de clientes en el estado especificado
     */
    long contarPorEstado(Cliente.EstadoCliente estado);

    /**
     * Elimina un cliente por su ID.
     * @param id ID del cliente a eliminar
     */
    void eliminar(String id);

    /**
     * Elimina físicamente un cliente (hard delete).
     * @param id ID del cliente a eliminar
     */
    void eliminarFisicamente(String id);

    /**
     * Busca clientes con filtros múltiples.
     * @param filtros Objeto con los criterios de búsqueda
     * @param pageable Información de paginación
     * @return Página con los clientes encontrados
     */
    Page<Cliente> buscarConFiltros(FiltrosBusquedaCliente filtros, Pageable pageable);

    /**
     * Obtiene estadísticas de clientes.
     * @return Objeto con estadísticas agregadas
     */
    EstadisticasCliente obtenerEstadisticas();

    /**
     * Clase para encapsular filtros de búsqueda.
     */
    class FiltrosBusquedaCliente {
        private String nombre;
        private String apellido;
        private String identificacion;
        private Cliente.TipoIdentificacion tipoIdentificacion;
        private Cliente.TipoCliente tipoCliente;
        private Cliente.EstadoCliente estado;
        private String email;
        private String telefono;
        private String ciudad;
        private boolean soloActivos;

        // Constructors
        public FiltrosBusquedaCliente() {}

        // Getters y Setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getApellido() { return apellido; }
        public void setApellido(String apellido) { this.apellido = apellido; }

        public String getIdentificacion() { return identificacion; }
        public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }

        public Cliente.TipoIdentificacion getTipoIdentificacion() { return tipoIdentificacion; }
        public void setTipoIdentificacion(Cliente.TipoIdentificacion tipoIdentificacion) { this.tipoIdentificacion = tipoIdentificacion; }

        public Cliente.TipoCliente getTipoCliente() { return tipoCliente; }
        public void setTipoCliente(Cliente.TipoCliente tipoCliente) { this.tipoCliente = tipoCliente; }

        public Cliente.EstadoCliente getEstado() { return estado; }
        public void setEstado(Cliente.EstadoCliente estado) { this.estado = estado; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }

        public String getCiudad() { return ciudad; }
        public void setCiudad(String ciudad) { this.ciudad = ciudad; }

        public boolean isSoloActivos() { return soloActivos; }
        public void setSoloActivos(boolean soloActivos) { this.soloActivos = soloActivos; }
    }

    /**
     * Clase para estadísticas de clientes.
     */
    class EstadisticasCliente {
        private long totalClientes;
        private long clientesActivos;
        private long clientesSuspendidos;
        private long clientesResidenciales;
        private long clientesComerciales;
        private long clientesIndustriales;
        private long clientesGubernamentales;

        // Constructors
        public EstadisticasCliente() {}

        public EstadisticasCliente(long totalClientes, long clientesActivos, long clientesSuspendidos,
                                 long clientesResidenciales, long clientesComerciales,
                                 long clientesIndustriales, long clientesGubernamentales) {
            this.totalClientes = totalClientes;
            this.clientesActivos = clientesActivos;
            this.clientesSuspendidos = clientesSuspendidos;
            this.clientesResidenciales = clientesResidenciales;
            this.clientesComerciales = clientesComerciales;
            this.clientesIndustriales = clientesIndustriales;
            this.clientesGubernamentales = clientesGubernamentales;
        }

        // Getters y Setters
        public long getTotalClientes() { return totalClientes; }
        public void setTotalClientes(long totalClientes) { this.totalClientes = totalClientes; }

        public long getClientesActivos() { return clientesActivos; }
        public void setClientesActivos(long clientesActivos) { this.clientesActivos = clientesActivos; }

        public long getClientesSuspendidos() { return clientesSuspendidos; }
        public void setSuspendidos(long clientesSuspendidos) { this.clientesSuspendidos = clientesSuspendidos; }

        public long getClientesResidenciales() { return clientesResidenciales; }
        public void setClientesResidenciales(long clientesResidenciales) { this.clientesResidenciales = clientesResidenciales; }

        public long getClientesComerciales() { return clientesComerciales; }
        public void setClientesComerciales(long clientesComerciales) { this.clientesComerciales = clientesComerciales; }

        public long getClientesIndustriales() { return clientesIndustriales; }
        public void setClientesIndustriales(long clientesIndustriales) { this.clientesIndustriales = clientesIndustriales; }

        public long getClientesGubernamentales() { return clientesGubernamentales; }
        public void setClientesGubernamentales(long clientesGubernamentales) { this.clientesGubernamentales = clientesGubernamentales; }
    }
}