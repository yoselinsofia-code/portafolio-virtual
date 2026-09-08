package com.portafolio.controller;

import com.portafolio.service.ActividadService;
import com.portafolio.service.SemanaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final SemanaService semanaService;
    private final ActividadService actividadService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalSemanas", semanaService.totalSemanas());
        model.addAttribute("totalActividades", actividadService.totalActividades());
        model.addAttribute("actividadesPublicadas", actividadService.totalPublicadas());
        model.addAttribute("actividadesPendientes", actividadService.totalPendientes());
        model.addAttribute("semanasPublicadas", semanaService.totalPublicadas());
        model.addAttribute("semanasPendientes", semanaService.totalPendientes());
        model.addAttribute("ultimasSemanas", semanaService.ultimasAgregadas());
        model.addAttribute("ultimasActividades", actividadService.ultimasAgregadas());
        model.addAttribute("activePage", "dashboard");
        return "admin/dashboard";
    }
}
