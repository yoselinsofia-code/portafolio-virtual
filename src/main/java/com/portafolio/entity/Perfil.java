package com.portafolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datos del perfil academico que se muestran en el portafolio publico.
 * Relacion 1 a 1 con Usuario.
 */
@Entity
@Table(name = "perfiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombres;
    private String apellidos;

    @Column(length = 255)
    private String fotoPerfil;

    private String carrera;
    private String instituto;
    private String curso;
    private String docente;

    @Column(length = 1000)
    private String descripcion;

    private String correo;
    private String github;
    private String linkedin;
    private String instagram;
    private String facebook;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
}
