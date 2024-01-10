package com.energia.enrique.medidorservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
@Table(name = "medidores")
public class MedidorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de medidor no puede estar en blanco")
    @Column(name = "numero_medidor", unique = true)
    private String numeroMedidor;

    @NotBlank(message = "El tipo de medidor no puede estar en blanco")
    @Column(name = "tipo_medidor")
    private String tipoMedidor;

    @NotNull(message = "El ID del contrato no puede ser nulo")
    @Column(name = "id_contrato")
    private Long idContrato;
}
