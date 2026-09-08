package com.portafolio.repository;

import com.portafolio.entity.Semana;
import com.portafolio.entity.enums.EstadoRegistro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SemanaRepository extends JpaRepository<Semana, Long> {

    List<Semana> findAllByOrderByOrdenVisualizacionAscNumeroSemanaAsc();

    List<Semana> findByEstadoOrderByOrdenVisualizacionAscNumeroSemanaAsc(EstadoRegistro estado);

    List<Semana> findTop5ByOrderByIdDesc();

    long countByEstado(EstadoRegistro estado);
}
