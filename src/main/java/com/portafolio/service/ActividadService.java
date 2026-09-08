package com.portafolio.service;

import com.portafolio.entity.Actividad;
import com.portafolio.entity.Semana;
import com.portafolio.entity.enums.EstadoRegistro;
import com.portafolio.repository.ActividadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActividadService {

    private final ActividadRepository actividadRepository;
    private final FileStorageService fileStorageService;

    public List<Actividad> listarPorSemana(Semana semana) {
        return actividadRepository.findBySemanaOrderByOrdenVisualizacionAscIdAsc(semana);
    }

    public List<Actividad> listarPublicadasPorSemana(Long semanaId) {
        return actividadRepository.findBySemanaIdAndEstadoOrderByOrdenVisualizacionAscIdAsc(semanaId, EstadoRegistro.PUBLICADO);
    }

    public List<Actividad> ultimasAgregadas() {
        return actividadRepository.findTop5ByOrderByIdDesc();
    }

    public Actividad obtenerPorId(Long id) {
        return actividadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada con id " + id));
    }

    @Transactional
    public Actividad guardar(Actividad actividad, MultipartFile imagen, MultipartFile archivoPdf) {
        if (imagen != null && !imagen.isEmpty()) {
            actividad.setImagen(fileStorageService.store(imagen, "actividades/imagenes"));
        }
        if (archivoPdf != null && !archivoPdf.isEmpty()) {
            actividad.setArchivoPdf(fileStorageService.store(archivoPdf, "actividades/pdf"));
        }
        return actividadRepository.save(actividad);
    }

    @Transactional
    public void eliminar(Long id) {
        Actividad actividad = obtenerPorId(id);
        if (actividad.getImagen() != null) fileStorageService.delete(actividad.getImagen());
        if (actividad.getArchivoPdf() != null) fileStorageService.delete(actividad.getArchivoPdf());
        actividadRepository.delete(actividad);
    }

    @Transactional
    public void cambiarEstado(Long id) {
        Actividad actividad = obtenerPorId(id);
        actividad.setEstado(actividad.getEstado() == EstadoRegistro.PUBLICADO
                ? EstadoRegistro.PENDIENTE
                : EstadoRegistro.PUBLICADO);
        actividadRepository.save(actividad);
    }

    public long totalActividades() {
        return actividadRepository.count();
    }

    public long totalPublicadas() {
        return actividadRepository.countByEstado(EstadoRegistro.PUBLICADO);
    }

    public long totalPendientes() {
        return actividadRepository.countByEstado(EstadoRegistro.PENDIENTE);
    }
}
