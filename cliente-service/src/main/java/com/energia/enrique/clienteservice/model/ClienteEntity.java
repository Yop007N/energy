package com.energia.enrique.clienteservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El RUC/CI no puede estar en blanco")
    @Column(name = "ruc_ci", unique = true, nullable = false)
    private String rucCi;

    @NotBlank(message = "El nombre no puede estar en blanco")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "La dirección no puede estar en blanco")
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull(message = "El tipo de cliente no puede ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente", nullable = false)
    private TipoCliente tipoCliente;

    // Otros atributos y métodos según sea necesario

    public enum TipoCliente {
        RESIDENCIAL,
        COMERCIAL,
        INDUSTRIAL
    }
}
