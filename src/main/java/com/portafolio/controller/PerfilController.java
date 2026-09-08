package com.portafolio.controller;

import com.portafolio.entity.Perfil;
import com.portafolio.service.PerfilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping
    public String verPerfil(Model model) {
        model.addAttribute("perfil", perfilService.obtenerPerfilPrincipal());
        model.addAttribute("activePage", "perfil");
        return "admin/perfil/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("perfil") Perfil perfil,
                           BindingResult result,
                           @RequestParam(value = "archivoFoto", required = false) MultipartFile archivoFoto,
                           Model model,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("activePage", "perfil");
            return "admin/perfil/form";
        }
        perfilService.guardar(perfil, archivoFoto, authentication.getName());
        redirectAttributes.addFlashAttribute("exito", "Perfil actualizado correctamente.");
        return "redirect:/admin/perfil";
    }
}
