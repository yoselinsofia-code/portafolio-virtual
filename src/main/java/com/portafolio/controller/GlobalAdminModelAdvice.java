package com.portafolio.controller;

import com.portafolio.service.PerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Inyecta datos comunes (perfil, para mostrar la foto en el sidebar)
 * en todas las vistas del dashboard administrativo.
 */
@ControllerAdvice(basePackages = "com.portafolio.controller")
@RequiredArgsConstructor
public class GlobalAdminModelAdvice {

    private final PerfilService perfilService;

    @ModelAttribute("perfilSidebar")
    public Object perfilSidebar() {
        try {
            return perfilService.obtenerPerfilPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
