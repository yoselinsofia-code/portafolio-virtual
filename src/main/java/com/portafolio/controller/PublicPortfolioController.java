package com.portafolio.controller;

import com.portafolio.entity.Actividad;
import com.portafolio.entity.Semana;
import com.portafolio.service.ActividadService;
import com.portafolio.service.PerfilService;
import com.portafolio.service.SemanaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Portafolio publico: muestra automaticamente todo lo publicado desde el
 * dashboard administrativo. No usa datos estaticos; todo viene de la BD.
 */
@Controller
@RequiredArgsConstructor
public class PublicPortfolioController {

    private final SemanaService semanaService;
    private final ActividadService actividadService;
    private final PerfilService perfilService;

    @GetMapping("/")
    public String inicio(Model model) {
        List<Semana> semanas = semanaService.listarPublicadas();
        long totalActividadesPublicadas = semanas.stream()
                .mapToLong(Semana::getActividadesPublicadas)
                .sum();

        model.addAttribute("semanas", semanas);
        model.addAttribute("totalActividadesPublicadas", totalActividadesPublicadas);
        model.addAttribute("perfil", perfilService.obtenerPerfilPrincipal());

        return "public/index";
    }

    @GetMapping("/semana/{id}")
    public String verSemana(@PathVariable Long id, Model model) {
        Semana semana = semanaService.obtenerPorId(id);
        List<Actividad> actividades =
                actividadService.listarPublicadasPorSemana(id);

        model.addAttribute("semana", semana);
        model.addAttribute("actividades", actividades);
        model.addAttribute("perfil", perfilService.obtenerPerfilPrincipal());

        return "public/semana";
    }

    @GetMapping("/actividad/{id}")
    public String verActividad(@PathVariable Long id, Model model) {
        Actividad actividad = actividadService.obtenerPorId(id);

        model.addAttribute("actividad", actividad);
        model.addAttribute("perfil", perfilService.obtenerPerfilPrincipal());

        return "public/actividad";
    }

    // ===================== SOBRE MÍ =====================

    @GetMapping("/sobre-mi")
    public String sobreMi(Model model) {
        model.addAttribute("perfil", perfilService.obtenerPerfilPrincipal());

        return "public/sobre-mi";
    }
}