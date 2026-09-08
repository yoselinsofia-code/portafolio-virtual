package com.portafolio.controller;

import com.portafolio.entity.Semana;
import com.portafolio.service.ActividadService;
import com.portafolio.service.SemanaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Panel de solo lectura para el docente: permite revisar el progreso,
 * las semanas y las actividades del portafolio sin poder editarlas,
 * crearlas ni eliminarlas.
 */
@Controller
@RequestMapping("/docente")
@RequiredArgsConstructor
public class DocenteController {

    private final SemanaService semanaService;
    private final ActividadService actividadService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalSemanas", semanaService.totalSemanas());
        model.addAttribute("totalActividades", actividadService.totalActividades());
        model.addAttribute("actividadesPublicadas", actividadService.totalPublicadas());
        model.addAttribute("actividadesPendientes", actividadService.totalPendientes());
        model.addAttribute("semanasPublicadas", semanaService.totalPublicadas());
        model.addAttribute("semanasPendientes", semanaService.totalPendientes());
        model.addAttribute("semanas", semanaService.listarTodas());
        model.addAttribute("ultimasActividades", actividadService.ultimasAgregadas());
        model.addAttribute("activePage", "dashboard");
        return "docente/dashboard";
    }

    @GetMapping("/semanas/{id}")
    public String verSemana(@PathVariable Long id, Model model) {
        Semana semana = semanaService.obtenerPorId(id);
        model.addAttribute("semana", semana);
        model.addAttribute("actividades", actividadService.listarPorSemana(semana));
        model.addAttribute("activePage", "semanas");
        return "docente/semana-detalle";
    }

    @GetMapping("/actividades/{id}")
    public String verActividad(@PathVariable Long id, Model model) {
        model.addAttribute("actividad", actividadService.obtenerPorId(id));
        model.addAttribute("activePage", "semanas");
        return "docente/actividad-detalle";
    }
}
