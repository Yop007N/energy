package com.energia.enrique.facturacionservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "factura")
public class FacturaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de factura no puede estar en blanco")
    @Size(max = 50, message = "El número de factura debe tener como máximo 50 caracteres")
    @Column(name = "numero_factura")
    private String numeroFactura;

    @NotBlank(message = "El número de timbrado no puede estar en blanco")
    @Size(max = 50, message = "El número de timbrado debe tener como máximo 50 caracteres")
    @Column(name = "numero_timbrado")
    private String numeroTimbrado;

    @NotNull(message = "La fecha de emisión no puede ser nula")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @NotNull(message = "La fecha de vencimiento no puede ser nula")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @DecimalMin(value = "0.01", message = "El monto total debe ser mayor que 0")
    @Column(name = "monto_total")
    private Double montoTotal;

    @NotBlank(message = "La categoría no puede estar en blanco")
    @Size(max = 50, message = "La categoría debe tener como máximo 50 caracteres")
    private String categoria;

    @NotBlank(message = "La actividad no puede estar en blanco")
    @Size(max = 50, message = "La actividad debe tener como máximo 50 caracteres")
    private String actividad;

    @NotNull(message = "El ID del contrato no puede ser nulo")
    @Column(name = "id_contrato")
    private Long idContrato;
}
