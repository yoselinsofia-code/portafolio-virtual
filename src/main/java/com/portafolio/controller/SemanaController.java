package com.portafolio.controller;

import com.portafolio.entity.Semana;
import com.portafolio.entity.enums.EstadoRegistro;
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
@RequestMapping("/admin/semanas")
@RequiredArgsConstructor
public class SemanaController {

    private final SemanaService semanaService;
    private final ActividadService actividadService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("semanas", semanaService.listarTodas());
        model.addAttribute("activePage", "semanas");
        return "admin/semanas/list";
    }

    @GetMapping("/nueva")
    public String nuevaForm(Model model) {
        model.addAttribute("semana", new Semana());
        model.addAttribute("activePage", "semanas");
        return "admin/semanas/form";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        model.addAttribute("semana", semanaService.obtenerPorId(id));
        model.addAttribute("activePage", "semanas");
        return "admin/semanas/form";
    }

    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        Semana semana = semanaService.obtenerPorId(id);
        model.addAttribute("semana", semana);
        model.addAttribute("actividades", actividadService.listarPorSemana(semana));
        model.addAttribute("activePage", "semanas");
        return "admin/semanas/detail";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("semana") Semana semana,
                           BindingResult result,
                           @RequestParam(value = "archivoImagen", required = false) MultipartFile archivoImagen,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("activePage", "semanas");
            return "admin/semanas/form";
        }
        if (semana.getEstado() == null) {
            semana.setEstado(EstadoRegistro.PENDIENTE);
        }
        semanaService.guardar(semana, archivoImagen);
        redirectAttributes.addFlashAttribute("exito", "Semana guardada correctamente.");
        return "redirect:/admin/semanas";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        semanaService.eliminar(id);
        redirectAttributes.addFlashAttribute("exito", "Semana eliminada correctamente.");
        return "redirect:/admin/semanas";
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        semanaService.cambiarEstado(id);
        redirectAttributes.addFlashAttribute("exito", "Estado actualizado.");
        return "redirect:/admin/semanas";
    }
}
