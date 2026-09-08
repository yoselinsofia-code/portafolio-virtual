package com.portafolio.controller;

import com.portafolio.entity.Actividad;
import com.portafolio.entity.Semana;
import com.portafolio.entity.enums.EstadoRegistro;
import com.portafolio.entity.enums.TipoActividad;
import com.portafolio.service.ActividadService;
import com.portafolio.service.SemanaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/actividades")
@RequiredArgsConstructor
public class ActividadController {

    private final ActividadService actividadService;
    private final SemanaService semanaService;

    @GetMapping("/nueva")
    public String nuevaForm(@RequestParam Long semanaId, Model model) {
        Actividad actividad = new Actividad();
        actividad.setSemana(semanaService.obtenerPorId(semanaId));
        model.addAttribute("actividad", actividad);
        model.addAttribute("tipos", TipoActividad.values());
        model.addAttribute("activePage", "semanas");
        return "admin/actividades/form";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        model.addAttribute("actividad", actividadService.obtenerPorId(id));
        model.addAttribute("tipos", TipoActividad.values());
        model.addAttribute("activePage", "semanas");
        return "admin/actividades/form";
    }

    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        model.addAttribute("actividad", actividadService.obtenerPorId(id));
        model.addAttribute("activePage", "semanas");
        return "admin/actividades/detail";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("actividad") Actividad actividad,
                           BindingResult result,
                           @RequestParam(value = "archivoImagen", required = false) MultipartFile archivoImagen,
                           @RequestParam(value = "archivoPdfUpload", required = false) MultipartFile archivoPdfUpload,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("tipos", TipoActividad.values());
            model.addAttribute("activePage", "semanas");
            return "admin/actividades/form";
        }
        if (actividad.getEstado() == null) {
            actividad.setEstado(EstadoRegistro.PENDIENTE);
        }
        Actividad guardada = actividadService.guardar(actividad, archivoImagen, archivoPdfUpload);
        redirectAttributes.addFlashAttribute("exito", "Actividad guardada correctamente.");
        Semana semana = guardada.getSemana();
        return "redirect:/admin/semanas/" + semana.getId();
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Actividad actividad = actividadService.obtenerPorId(id);
        Long semanaId = actividad.getSemana().getId();
        actividadService.eliminar(id);
        redirectAttributes.addFlashAttribute("exito", "Actividad eliminada correctamente.");
        return "redirect:/admin/semanas/" + semanaId;
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Actividad actividad = actividadService.obtenerPorId(id);
        Long semanaId = actividad.getSemana().getId();
        actividadService.cambiarEstado(id);
        redirectAttributes.addFlashAttribute("exito", "Estado actualizado.");
        return "redirect:/admin/semanas/" + semanaId;
    }
}
