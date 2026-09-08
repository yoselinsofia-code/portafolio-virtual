package com.portafolio.repository;

import com.portafolio.entity.Actividad;
import com.portafolio.entity.Semana;
import com.portafolio.entity.enums.EstadoRegistro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {

    List<Actividad> findBySemanaOrderByOrdenVisualizacionAscIdAsc(Semana semana);

    List<Actividad> findBySemanaIdAndEstadoOrderByOrdenVisualizacionAscIdAsc(Long semanaId, EstadoRegistro estado);

    List<Actividad> findTop5ByOrderByIdDesc();

    long countByEstado(EstadoRegistro estado);
}
