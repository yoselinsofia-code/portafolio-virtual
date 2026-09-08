package com.portafolio.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Tras un login exitoso, redirige a cada usuario segun su rol:
 * ROLE_ADMIN -> panel de administracion (edicion completa)
 * ROLE_DOCENTE -> panel de solo lectura para revisar el portafolio
 */
@Component
public class RoleBasedAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws IOException, ServletException {
        boolean esAdministrador = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(rol -> rol.equals("ROLE_ADMIN"));

        String destino = esAdministrador ? "/admin/dashboard" : "/docente/dashboard";
        response.sendRedirect(request.getContextPath() + destino);
    }
}
