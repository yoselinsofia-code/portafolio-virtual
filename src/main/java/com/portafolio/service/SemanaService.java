package com.portafolio.service;

import com.portafolio.entity.Semana;
import com.portafolio.entity.enums.EstadoRegistro;
import com.portafolio.repository.SemanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemanaService {

    private final SemanaRepository semanaRepository;
    private final FileStorageService fileStorageService;

    public List<Semana> listarTodas() {
        return semanaRepository.findAllByOrderByOrdenVisualizacionAscNumeroSemanaAsc();
    }

    public List<Semana> listarPublicadas() {
        return semanaRepository.findByEstadoOrderByOrdenVisualizacionAscNumeroSemanaAsc(EstadoRegistro.PUBLICADO);
    }

    public List<Semana> ultimasAgregadas() {
        return semanaRepository.findTop5ByOrderByIdDesc();
    }

    public Semana obtenerPorId(Long id) {
        return semanaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Semana no encontrada con id " + id));
    }

    /**
     * Guarda una semana nueva o actualiza una existente.
     * IMPORTANTE: cuando se edita, se recupera la entidad ya persistida y se
     * actualizan solo los campos del formulario, preservando la imagen de
     * portada existente (si no se sube una nueva) y la lista de actividades
     * ya asociadas (evita que Hibernate las elimine por orphanRemoval).
     */
    @Transactional
    public Semana guardar(Semana semanaForm, MultipartFile imagenPortada) {
        Semana semana;
        if (semanaForm.getId() != null && semanaRepository.existsById(semanaForm.getId())) {
            semana = obtenerPorId(semanaForm.getId());
        } else {
            semana = new Semana();
        }

        semana.setNumeroSemana(semanaForm.getNumeroSemana());
        semana.setTitulo(semanaForm.getTitulo());
        semana.setDescripcion(semanaForm.getDescripcion());
        semana.setFecha(semanaForm.getFecha());
        semana.setEstado(semanaForm.getEstado());
        semana.setOrdenVisualizacion(semanaForm.getOrdenVisualizacion());

        if (imagenPortada != null && !imagenPortada.isEmpty()) {
            if (semana.getImagenPortada() != null) {
                fileStorageService.delete(semana.getImagenPortada());
            }
            String ruta = fileStorageService.store(imagenPortada, "semanas");
            semana.setImagenPortada(ruta);
        }
        // Si no se sube una nueva imagen, semana.getImagenPortada() conserva la existente.

        return semanaRepository.save(semana);
    }

    @Transactional
    public void eliminar(Long id) {
        Semana semana = obtenerPorId(id);
        if (semana.getImagenPortada() != null) {
            fileStorageService.delete(semana.getImagenPortada());
        }
        semanaRepository.delete(semana);
    }

    @Transactional
    public void cambiarEstado(Long id) {
        Semana semana = obtenerPorId(id);
        semana.setEstado(semana.getEstado() == EstadoRegistro.PUBLICADO
                ? EstadoRegistro.PENDIENTE
                : EstadoRegistro.PUBLICADO);
        semanaRepository.save(semana);
    }

    public long totalSemanas() {
        return semanaRepository.count();
    }

    public long totalPublicadas() {
        return semanaRepository.countByEstado(EstadoRegistro.PUBLICADO);
    }

    public long totalPendientes() {
        return semanaRepository.countByEstado(EstadoRegistro.PENDIENTE);
    }
}
