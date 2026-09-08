package com.portafolio.repository;

import com.portafolio.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByUsuarioId(Long usuarioId);
    Optional<Perfil> findFirstByOrderByIdAsc();
}
