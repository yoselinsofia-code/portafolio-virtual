package com.portafolio.entity;

import com.portafolio.entity.enums.EstadoRegistro;
import com.portafolio.entity.enums.TipoActividad;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Representa una actividad academica perteneciente a una unica semana.
 * Actividad N ---- 1 Semana
 */
@Entity
@Table(name = "actividades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semana_id", nullable = false)
    private Semana semana;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(length = 3000)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_actividad", nullable = false, length = 30)
    private TipoActividad tipoActividad;

    @Column(length = 255)
    private String imagen;

    @Column(name = "archivo_pdf", length = 255)
    private String archivoPdf;

    @Column(name = "enlace_externo", length = 500)
    private String enlaceExterno;

    @Lob
    @Column(name = "codigo_java", columnDefinition = "LONGTEXT")
    private String codigoJava;

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoRegistro estado = EstadoRegistro.PENDIENTE;

    @Column(name = "orden_visualizacion")
    private Integer ordenVisualizacion = 0;
}
