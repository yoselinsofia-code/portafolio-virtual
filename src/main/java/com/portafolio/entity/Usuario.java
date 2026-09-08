package com.portafolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Usuario administrador del sistema. Controla el acceso al dashboard.
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false, length = 60)
    private String username;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Email
    @Column(length = 120)
    private String email;

    @Column(nullable = false)
    private boolean habilitado = true;

    @Column(nullable = false, length = 30)
    private String rol = "ROLE_ADMIN";

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Perfil perfil;
}
