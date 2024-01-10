package com.energia.enrique.contratoservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;


import java.util.Objects;

@Entity
@Table(name = "contratos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El N° de Contrato no puede estar en blanco")
    @Column(name = "numero_contrato", unique = true, nullable = false)
    private String numeroContrato;

    @NotBlank(message = "El Titular del Contrato no puede estar en blanco")
    @Column(name = "titular_contrato", nullable = false)
    private String titularContrato;

    @NotBlank(message = "La Dirección de Suministro no puede estar en blanco")
    @Column(name = "direccion_suministro", nullable = false)
    private String direccionSuministro;

    @NotBlank(message = "El Ciclo de Facturación no puede estar en blanco")
    @Column(name = "ciclo_facturacion", nullable = false)
    private String cicloFacturacion;

    @NotNull(message = "El ID del Cliente no puede ser nulo")
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ContratoEntity that = (ContratoEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
