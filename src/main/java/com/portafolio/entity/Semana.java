package com.portafolio.entity;

import com.portafolio.entity.enums.EstadoRegistro;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una semana academica. Una semana puede tener muchas actividades.
 * Semana 1 ----&lt; Actividades
 */
@Entity
@Table(name = "semanas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Semana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero_semana", nullable = false)
    private Integer numeroSemana;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(length = 2000)
    private String descripcion;

    @Column(name = "imagen_portada", length = 255)
    private String imagenPortada;

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoRegistro estado = EstadoRegistro.PENDIENTE;

    @Column(name = "orden_visualizacion")
    private Integer ordenVisualizacion = 0;

    @OneToMany(mappedBy = "semana", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Actividad> actividades = new ArrayList<>();

    public long getTotalActividades() {
        return actividades == null ? 0 : actividades.size();
    }

    public long getActividadesPublicadas() {
        if (actividades == null) return 0;
        return actividades.stream().filter(a -> a.getEstado() == EstadoRegistro.PUBLICADO).count();
    }
}
